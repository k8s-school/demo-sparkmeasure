package com.example.sparkmeasure

import javax.management._
import java.lang.management.ManagementFactory

trait SparkMeasureMetricsMBean {
  def getExecutorRunTime(): Long
  def setExecutorRunTime(value: Long): Unit
}

class SparkMeasureMetrics extends SparkMeasureMetricsMBean {
  @volatile var executorRunTime: Long = 0L
  override def getExecutorRunTime(): Long = executorRunTime
  override def setExecutorRunTime(value: Long): Unit = {
    executorRunTime = value
  }
}

object JMXPublisher {
  val mbs: MBeanServer = ManagementFactory.getPlatformMBeanServer
  val mbean = new SparkMeasureMetrics()
  val name = new ObjectName("sparkmeasure:type=Metrics")

  def register(): Unit = {
    if (!mbs.isRegistered(name)) {
      mbs.registerMBean(mbean, name)
    }
  }

  def setExecutorRunTime(value: Long): Unit = {
    mbean.setExecutorRunTime(value)
  }
}
