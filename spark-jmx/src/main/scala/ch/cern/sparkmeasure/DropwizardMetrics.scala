package ch.cern.metrics

import com.codahale.metrics.{Gauge, MetricRegistry}
import com.codahale.metrics.jmx.JmxReporter

object DropwizardMetrics {
  val registry = new MetricRegistry()

  // Simple cache local pour éviter double registration
  private val knownMetrics = scala.collection.mutable.Set[String]()

  // Démarre le JMX reporter une seule fois
  private val reporter: JmxReporter = JmxReporter
    .forRegistry(registry)
    .inDomain("sparkmeasure.metrics") // <== domaine JMX
    .build()

  reporter.start()

  def setGauge(name: String, value: Double): Unit = {
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
