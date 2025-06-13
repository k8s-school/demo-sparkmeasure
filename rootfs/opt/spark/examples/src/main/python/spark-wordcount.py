"""
count words from a stream of text data using Spark Streaming.
This example connects to a TCP socket (e.g., using netcat) and counts the words in the incoming text data.
To run this example, you need to have a TCP server sending text data to the specified host and port.
"""
from pyspark.sql import SparkSession
from pyspark.sql.functions import explode, split
from sparkmeasure import StageMetrics
import datetime


def main():
    spark = SparkSession.builder \
        .appName("StructuredStreamingWordCountWithStageMetrics") \
        .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    # Lecture depuis netcat TCP (Kubernetes Service)
    lines = spark.readStream \
        .format("socket") \
        .option("host", "word-emitter.word-emitter") \
        .option("port", 9999) \
        .load()

    words = lines.select(explode(split(lines.value, " ")).alias("word"))
    word_counts = words.groupBy("word").count()

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

    # Définir le pipeline avec foreachBatch
    query = word_counts.writeStream \
        .outputMode("complete") \
        .foreachBatch(process_batch) \
        .option("checkpointLocation", "/tmp/spark-checkpoint/wordcount") \
        .start()

    query.awaitTermination()


if __name__ == "__main__":
    main()
