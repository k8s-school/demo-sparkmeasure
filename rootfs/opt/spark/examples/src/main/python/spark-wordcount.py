"""
count words from a stream of text data using Spark Streaming.
This example connects to a TCP socket (e.g., using netcat) and counts the words in the incoming text data.
To run this example, you need to have a TCP server sending text data to the specified host and port.
"""

from functools import partial

import metrics

from pyspark.sql import SparkSession
from pyspark.sql.functions import explode, split


# Définition du writer
def write_to_parquet(df):
    df.write.mode("append").parquet("/tmp/wordcount")

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

    # Création de la fonction partielle qui injecte le writer
    batch_processor = partial(metrics.process_batch, spark_session=spark, write_fn=write_to_parquet)

    # Définir le pipeline avec foreachBatch
    query = word_counts.writeStream \
        .outputMode("complete") \
        .foreachBatch(batch_processor) \
        .option("checkpointLocation", "/tmp/spark-checkpoint/wordcount") \
        .start()

    query.awaitTermination()

if __name__ == "__main__":
    main()
