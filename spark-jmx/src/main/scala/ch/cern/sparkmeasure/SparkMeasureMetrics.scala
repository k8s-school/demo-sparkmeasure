package ch.cern.sparkmeasure

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
    println(s"[JMX] Setting metric: $key = $value")
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
      case _ => println(s"[JMX] Unknown metric: $key")
    }
  }

  // Accessors for metrics
  override def getNumStages(): Int = numStages
  override def getNumTasks(): Int = numTasks
  override def getElapsedTime(): Long = elapsedTime
  override def getStageDuration(): Long = stageDuration
  override def getExecutorRunTime(): Long = executorRunTime
  override def getExecutorCpuTime(): Long = executorCpuTime
  override def getExecutorDeserializeTime(): Long = executorDeserializeTime
  override def getExecutorDeserializeCpuTime(): Long = executorDeserializeCpuTime
  override def getResultSerializationTime(): Long = resultSerializationTime
  override def getJvmGCTime(): Long = jvmGCTime
  override def getShuffleFetchWaitTime(): Long = shuffleFetchWaitTime
  override def getShuffleWriteTime(): Long = shuffleWriteTime
  override def getResultSize(): Long = resultSize
  override def getDiskBytesSpilled(): Long = diskBytesSpilled
  override def getMemoryBytesSpilled(): Long = memoryBytesSpilled
  override def getPeakExecutionMemory(): Long = peakExecutionMemory
  override def getRecordsRead(): Long = recordsRead
  override def getBytesRead(): Long = bytesRead
  override def getRecordsWritten(): Long = recordsWritten
  override def getBytesWritten(): Long = bytesWritten
  override def getShuffleRecordsRead(): Long = shuffleRecordsRead
  override def getShuffleTotalBlocksFetched(): Long = shuffleTotalBlocksFetched
  override def getShuffleLocalBlocksFetched(): Long = shuffleLocalBlocksFetched
  override def getShuffleRemoteBlocksFetched(): Long = shuffleRemoteBlocksFetched
  override def getShuffleTotalBytesRead(): Long = shuffleTotalBytesRead
  override def getShuffleLocalBytesRead(): Long = shuffleLocalBytesRead
  override def getShuffleRemoteBytesRead(): Long = shuffleRemoteBytesRead
  override def getShuffleRemoteBytesReadToDisk(): Long = shuffleRemoteBytesReadToDisk
  override def getShuffleBytesWritten(): Long = shuffleBytesWritten
  override def getShuffleRecordsWritten(): Long = shuffleRecordsWritten

}
