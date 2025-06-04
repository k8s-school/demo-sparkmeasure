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

  // private val name = new ObjectName(s"sparkmeasure:type=Metrics,namespace=$namespace,pod=$podName")
  val name = new ObjectName("sparkmeasure:type=Metrics")
  def register(): Unit = {
    if (!mbs.isRegistered(name)) {
      mbs.registerMBean(mbean, name)
    }
  }

  def setMetricsMap(metrics: java.util.Map[String, Number]): Unit = {
    mbean.setMetrics(metrics)
  }

}
