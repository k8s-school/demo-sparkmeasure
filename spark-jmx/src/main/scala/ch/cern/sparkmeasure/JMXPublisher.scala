package ch.cern.sparkmeasure

import javax.management._
import java.lang.management.ManagementFactory
import java.io.File

import scala.collection.concurrent.TrieMap
import scala.io.Source
import scala.collection.JavaConverters._

object JMXPublisher {
  val mbs: MBeanServer = ManagementFactory.getPlatformMBeanServer
  val mbean = new SparkMeasureMetrics()

  private def getNamespace(): String = {
    val path = "/var/run/secrets/kubernetes.io/serviceaccount/namespace"
    val file = new File(path)
    if (file.exists && file.canRead)
      Source.fromFile(file).getLines().mkString.trim
    else "unknown"
  }

  private def getPodName(): String = {
    sys.env.getOrElse("HOSTNAME", "unknown")
  }

  private val namespace = getNamespace()
  private val podName = getPodName()

  private val name = new ObjectName(s"sparkmeasure:type=Metrics,namespace=$namespace,pod=$podName")


  def register(): Unit = {
    println("DEBUGXXXXX: Calling register")
    if (!mbs.isRegistered(name)) {
      mbs.registerMBean(mbean, name)
    }
  }

  def setMetrics(metrics: java.util.Map[String, Number]): Unit = {
    println("DEBUGXXXXX: Calling setMetrics")
    metrics.asScala.foreach {
      case (key: String, value: Number) =>
        println(s"DEBUGXXXXX: Setting metric $key to value $value")
        mbean.setMetric(key, value.doubleValue())
      case _ =>
        println("Skipping invalid metric entry (not String -> Number)")
    }
  }
}
