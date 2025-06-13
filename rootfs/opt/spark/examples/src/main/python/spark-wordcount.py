"""
count words from a stream of text data using Spark Streaming.
This example connects to a TCP socket (e.g., using netcat) and counts the words in the incoming text data.
To run this example, you need to have a TCP server sending text data to the specified host and port.
"""
from pyspark.sql import SparkSession
from pyspark.sql.functions import explode, split
# from sparkmeasure import SQLMetrics


def main():
    spark = SparkSession.builder \
        .appName("StructuredStreamingWordCount") \
        .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    # Configure sparkmeasure
    # sql_metrics = SQLMetrics(spark)
    # sql_metrics.begin()

    # Stream depuis Netcat
    lines = spark.readStream \
        .format("socket") \
        .option("host", "word-emitter.word-emitter") \
        .option("port", 9999) \
        .load()

    words = lines.select(explode(split(lines.value, " ")).alias("word"))
    word_counts = words.groupBy("word").count()

    # # Mesure de performance via transform()
    # def instrumented(df):
    #     # spark-measure hook
    #     sql_metrics.begin()
    #     df.cache().count()  # force l'exécution
    #     sql_metrics.end()
    #     sql_metrics.print_report()
    #     return df

    # word_counts = word_counts.transform(instrumented)

    query = word_counts.writeStream \
        .outputMode("complete") \
        .format("console") \
        .option("truncate", False) \
        .option("checkpointLocation", "/tmp/spark-checkpoint/wordcount") \
        .start()

    query.awaitTermination()

if __name__ == "__main__":
    main()

