package ch.cern.metrics

import java.io.File

import scala.io.Source

import com.codahale.metrics.{Gauge, MetricRegistry}
import com.codahale.metrics.jmx.JmxReporter

object DropwizardMetrics {
  val registry = new MetricRegistry()

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

  // Simple cache local pour éviter double registration
  private val knownMetrics = scala.collection.mutable.Set[String]()

  // Démarre le JMX reporter une seule fois
  private val reporter: JmxReporter = JmxReporter
    .forRegistry(registry)
    .inDomain("sparkmeasure.metrics") // <== domaine JMX
    .build()

  reporter.start()

  def setGauge(shortname: String, value: Double): Unit = {
    val name = getNamespace() + "." + getPodName() + "." + shortname
    if (!knownMetrics.contains(name)) {
      registry.register(name, new Gauge[Double] {
        override def getValue: Double = values.getOrElse(name, 0.0)
      })
      knownMetrics += name
    }
    values.update(name, value)
  }

  private val values = scala.collection.concurrent.TrieMap[String, Double]()
}
