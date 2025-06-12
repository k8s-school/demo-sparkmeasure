package ch.cern.metrics

import java.io.File
import org.slf4j.LoggerFactory
import scala.io.Source

import com.codahale.metrics.{Counter, Gauge, MetricRegistry}
import com.codahale.metrics.jmx.JmxReporter

object DropwizardMetrics {
  val registry = new MetricRegistry()
  private val logger = LoggerFactory.getLogger(getClass)

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
  private val knownGauges = scala.collection.mutable.Set[String]()
  private val knownCounters = scala.collection.mutable.Set[String]()

  // Démarre le JMX reporter une seule fois
  private val reporter: JmxReporter = JmxReporter
    .forRegistry(registry)
    .inDomain("sparkmeasure.metrics") // <== domaine JMX
    .build()

  reporter.start()

  def setGauge(shortname: String, value: Double): Unit = {
    logger.debug(s"[JMX] Setting gauge: $shortname = $value")
    val name = getNamespace() + "." + getPodName() + "." + shortname
    if (!knownGauges.contains(name)) {
      registry.register(name, new Gauge[Double] {
        override def getValue: Double = gauges.getOrElse(name, 0.0)
      })
      knownGauges += name
    }
    gauges.update(name, value)
  }

  def setCounter(shortname: String, value: Long): Unit = {
    logger.debug(s"[JMX] Setting counter: $shortname = $value")
    val name = getNamespace() + "." + getPodName() + "." + shortname
    if (!knownCounters.contains(name)) {
      registry.register(name, new Counter())
      knownCounters += name
    }
    counters.update(name, value)
  }

  private val gauges = scala.collection.concurrent.TrieMap[String, Double]()
  private val counters = scala.collection.concurrent.TrieMap[String, Long]()
}
