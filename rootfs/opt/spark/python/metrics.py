
from sparkmeasure import StageMetrics

# Callback foreachBatch avec StageMetrics
def process_batch(df, batch_id):
    stagemetrics = StageMetrics(spark)
    stagemetrics.begin()

    df.cache().count()  # force plan exécution
    stagemetrics.end()

    # Sauvegarde dans un fichier local JSON
    timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
    print("\n----------------------")
    print(f"Metrics data for batch {batch_id} at {timestamp}")
    print("----------------------")
    # print report to standard output
    stagemetrics.print_report()

    # get metrics data as a dictionary
    metrics = stagemetrics.aggregate_stagemetrics()
    print(f"metrics elapsedTime = {metrics.get('elapsedTime')}")


def publish_metrics(spark_session, metrics: Dict[str, Union[float, int]]):
    try:
        publish_metrics_count = 0
        dropwizard = spark_session._jvm.ch.cern.metrics.DropwizardMetrics
        for key, value in metrics.items():
            # or setGauge
            dropwizard.setCounter(key, float(value))
            # Example counter to track the number of times metrics have been published
            publish_metrics_count += 1

        dropwizard.setCounter("metrics_published_total", publish_metrics_count)

        print(f"[INFO] {len(metrics)} Dropwizard metrics published via JMX")
    except Exception as e:
        print(f"[ERROR] Failed to publish Dropwizard metrics: {e}")