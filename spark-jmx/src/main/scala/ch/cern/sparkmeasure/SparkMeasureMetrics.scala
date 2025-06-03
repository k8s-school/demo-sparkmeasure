package ch.cern.sparkmeasure
import javax.management._
import java.lang.management.ManagementFactory
import scala.collection.concurrent.TrieMap

class SparkMeasureMetrics extends SparkMeasureMetricsMBean {
  private val metrics = TrieMap.empty[String, Double]

  override def setMetric(key: String, value: Double): Unit = {
    metrics.put(key, value)
  }

  override def getMetric(key: String): Double = {
    metrics.getOrElse(key, 0.0)
  }

  override def getAllMetricNames(): Array[String] = {
    metrics.keys.toArray
  }
}