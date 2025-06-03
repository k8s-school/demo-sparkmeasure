package ch.cern.sparkmeasure

trait SparkMeasureMetricsMBean {
  def setMetric(key: String, value: Double): Unit
  def getMetric(key: String): Double
  def getAllMetricNames(): Array[String]
}