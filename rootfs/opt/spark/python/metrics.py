from typing import Union, Dict

import logging
from sparkmeasure import StageMetrics
import datetime

logger = logging.getLogger(__name__)

# Callback foreachBatch avec StageMetrics
def process_batch(df, batch_id, spark_session):
    stagemetrics = StageMetrics(spark_session)
    stagemetrics.begin()

    df.cache().count()  # force plan exécution
    stagemetrics.end()

    # Sauvegarde dans un fichier local JSON
    timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
    logger.info("\n----------------------")
    logger.info("Metrics data for batch %s at %s", batch_id, timestamp)
    logger.info("----------------------")
    # print report to standard output
    stagemetrics.print_report()

    # get metrics data as a dictionary
    metrics = stagemetrics.aggregate_stagemetrics()
    logger.info("metrics elapsedTime = %s", metrics.get('elapsedTime'))


def publish_metrics(spark_session, metrics: Dict[str, Union[float, int]]):
    try:
        publish_metrics_count = 0
        dropwizard = spark_session._jvm.ch.cern.metrics.DropwizardMetrics
        for key, value in metrics.items():
            # or setGauge
            dropwizard.setMetric(key, float(value), False)
            # Example counter to track the number of times metrics have been published
            publish_metrics_count += 1

        dropwizard.setMetric("metrics_published_total", float(publish_metrics_count), True)

        logger.info("%d Dropwizard metrics published via JMX", len(metrics))
    except Exception as e:
        logger.error("Failed to publish Dropwizard metrics: %s", e)
