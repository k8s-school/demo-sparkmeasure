package com.example.sparkmeasure

class SparkMeasureMetrics extends SparkMeasureMetricsMBean {
  @volatile private var numStages = 0
  @volatile private var numTasks = 0
  @volatile private var elapsedTime = 0L
  @volatile private var stageDuration = 0L
  @volatile private var executorRunTime = 0L
  @volatile private var executorCpuTime = 0L
  @volatile private var executorDeserializeTime = 0L
  @volatile private var executorDeserializeCpuTime = 0L
  @volatile private var resultSerializationTime = 0L
  @volatile private var jvmGCTime = 0L
  @volatile private var shuffleFetchWaitTime = 0L
  @volatile private var shuffleWriteTime = 0L
  @volatile private var resultSize = 0L
  @volatile private var diskBytesSpilled = 0L
  @volatile private var memoryBytesSpilled = 0L
  @volatile private var peakExecutionMemory = 0L
  @volatile private var recordsRead = 0L
  @volatile private var bytesRead = 0L
  @volatile private var recordsWritten = 0L
  @volatile private var bytesWritten = 0L
  @volatile private var shuffleRecordsRead = 0L
  @volatile private var shuffleTotalBlocksFetched = 0L
  @volatile private var shuffleLocalBlocksFetched = 0L
  @volatile private var shuffleRemoteBlocksFetched = 0L
  @volatile private var shuffleTotalBytesRead = 0L
  @volatile private var shuffleLocalBytesRead = 0L
  @volatile private var shuffleRemoteBytesRead = 0L
  @volatile private var shuffleRemoteBytesReadToDisk = 0L
  @volatile private var shuffleBytesWritten = 0L
  @volatile private var shuffleRecordsWritten = 0L

  def setMetrics(metrics: java.util.Map[String, Number]): Unit = {
    import scala.collection.JavaConverters._

    metrics.asScala.foreach { case (key, value) =>
      set(key, value.doubleValue())
    }
    println(s"[JMX] ${metrics.size} metrics updated.")
  }

  def set(key: String, value: Number): Unit = {
    val longVal = value.longValue()
    key match {
      case "numStages" => numStages = value.intValue()
      case "numTasks" => numTasks = value.intValue()
      case "elapsedTime" => elapsedTime = longVal
      case "stageDuration" => stageDuration = longVal
      case "executorRunTime" => executorRunTime = longVal
      case "executorCpuTime" => executorCpuTime = longVal
      case "executorDeserializeTime" => executorDeserializeTime = longVal
      case "executorDeserializeCpuTime" => executorDeserializeCpuTime = longVal
      case "resultSerializationTime" => resultSerializationTime = longVal
      case "jvmGCTime" => jvmGCTime = longVal
      case "shuffleFetchWaitTime" => shuffleFetchWaitTime = longVal
      case "shuffleWriteTime" => shuffleWriteTime = longVal
      case "resultSize" => resultSize = longVal
      case "diskBytesSpilled" => diskBytesSpilled = longVal
      case "memoryBytesSpilled" => memoryBytesSpilled = longVal
      case "peakExecutionMemory" => peakExecutionMemory = longVal
      case "recordsRead" => recordsRead = longVal
      case "bytesRead" => bytesRead = longVal
      case "recordsWritten" => recordsWritten = longVal
      case "bytesWritten" => bytesWritten = longVal
      case "shuffleRecordsRead" => shuffleRecordsRead = longVal
      case "shuffleTotalBlocksFetched" => shuffleTotalBlocksFetched = longVal
      case "shuffleLocalBlocksFetched" => shuffleLocalBlocksFetched = longVal
      case "shuffleRemoteBlocksFetched" => shuffleRemoteBlocksFetched = longVal
      case "shuffleTotalBytesRead" => shuffleTotalBytesRead = longVal
      case "shuffleLocalBytesRead" => shuffleLocalBytesRead = longVal
      case "shuffleRemoteBytesRead" => shuffleRemoteBytesRead = longVal
      case "shuffleRemoteBytesReadToDisk" => shuffleRemoteBytesReadToDisk = longVal
      case "shuffleBytesWritten" => shuffleBytesWritten = longVal
      case "shuffleRecordsWritten" => shuffleRecordsWritten = longVal
      case _ => // ignore unknowns
    }
  }

  // Accesseurs
  def getNumStages(): Int = numStages
  def getNumTasks(): Int = numTasks
  def getElapsedTime(): Long = elapsedTime
  def getStageDuration(): Long = stageDuration
  def getExecutorRunTime(): Long = executorRunTime
  def getExecutorCpuTime(): Long = executorCpuTime
  def getExecutorDeserializeTime(): Long = executorDeserializeTime
  def getExecutorDeserializeCpuTime(): Long = executorDeserializeCpuTime
  def getResultSerializationTime(): Long = resultSerializationTime
  def getJvmGCTime(): Long = jvmGCTime
  def getShuffleFetchWaitTime(): Long = shuffleFetchWaitTime
  def getShuffleWriteTime(): Long = shuffleWriteTime
  def getResultSize(): Long = resultSize
  def getDiskBytesSpilled(): Long = diskBytesSpilled
  def getMemoryBytesSpilled(): Long = memoryBytesSpilled
  def getPeakExecutionMemory(): Long = peakExecutionMemory
  def getRecordsRead(): Long = recordsRead
  def getBytesRead(): Long = bytesRead
  def getRecordsWritten(): Long = recordsWritten
  def getBytesWritten(): Long = bytesWritten
  def getShuffleRecordsRead(): Long = shuffleRecordsRead
  def getShuffleTotalBlocksFetched(): Long = shuffleTotalBlocksFetched
  def getShuffleLocalBlocksFetched(): Long = shuffleLocalBlocksFetched
  def getShuffleRemoteBlocksFetched(): Long = shuffleRemoteBlocksFetched
  def getShuffleTotalBytesRead(): Long = shuffleTotalBytesRead
  def getShuffleLocalBytesRead(): Long = shuffleLocalBytesRead
  def getShuffleRemoteBytesRead(): Long = shuffleRemoteBytesRead
  def getShuffleRemoteBytesReadToDisk(): Long = shuffleRemoteBytesReadToDisk
  def getShuffleBytesWritten(): Long = shuffleBytesWritten
  def getShuffleRecordsWritten(): Long = shuffleRecordsWritten
}
