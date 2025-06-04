package ch.cern.sparkmeasure

trait SparkMeasureMetricsMBean {
  def getNumStages(): Int
  def getNumTasks(): Int
  def getElapsedTime(): Long
  def getStageDuration(): Long
  def getExecutorRunTime(): Long
  def getExecutorCpuTime(): Long
  def getExecutorDeserializeTime(): Long
  def getExecutorDeserializeCpuTime(): Long
  def getResultSerializationTime(): Long
  def getJvmGCTime(): Long
  def getShuffleFetchWaitTime(): Long
  def getShuffleWriteTime(): Long
  def getResultSize(): Long
  def getDiskBytesSpilled(): Long
  def getMemoryBytesSpilled(): Long
  def getPeakExecutionMemory(): Long
  def getRecordsRead(): Long
  def getBytesRead(): Long
  def getRecordsWritten(): Long
  def getBytesWritten(): Long
  def getShuffleRecordsRead(): Long
  def getShuffleTotalBlocksFetched(): Long
  def getShuffleLocalBlocksFetched(): Long
  def getShuffleRemoteBlocksFetched(): Long
  def getShuffleTotalBytesRead(): Long
  def getShuffleLocalBytesRead(): Long
  def getShuffleRemoteBytesRead(): Long
  def getShuffleRemoteBytesReadToDisk(): Long
  def getShuffleBytesWritten(): Long
  def getShuffleRecordsWritten(): Long

  // Setters for metrics
  def setNumStages(numStages: Int): Unit
  def setNumTasks(numTasks: Int): Unit
  def setElapsedTime(elapsedTime: Long): Unit
  def setStageDuration(stageDuration: Long): Unit
  def setExecutorRunTime(executorRunTime: Long): Unit
  def setExecutorCpuTime(executorCpuTime: Long): Unit
  def setExecutorDeserializeTime(executorDeserializeTime: Long): Unit
  def setExecutorDeserializeCpuTime(executorDeserializeCpuTime: Long): Unit
  def setResultSerializationTime(resultSerializationTime: Long): Unit
  def setJvmGCTime(jvmGCTime: Long): Unit
  def setShuffleFetchWaitTime(shuffleFetchWaitTime: Long): Unit
  def setShuffleWriteTime(shuffleWriteTime: Long): Unit
  def setResultSize(resultSize: Long): Unit
  def setDiskBytesSpilled(diskBytesSpilled: Long): Unit
  def setMemoryBytesSpilled(memoryBytesSpilled: Long): Unit
  def setPeakExecutionMemory(peakExecutionMemory: Long): Unit
  def setRecordsRead(recordsRead: Long): Unit
  def setBytesRead(bytesRead: Long): Unit
  def setRecordsWritten(recordsWritten: Long): Unit
  def setBytesWritten(bytesWritten: Long): Unit
  def setShuffleRecordsRead(shuffleRecordsRead: Long): Unit
  def setShuffleTotalBlocksFetched(shuffleTotalBlocksFetched: Long): Unit
  def setShuffleLocalBlocksFetched(shuffleLocalBlocksFetched: Long): Unit
  def setShuffleRemoteBlocksFetched(shuffleRemoteBlocksFetched: Long): Unit
  def setShuffleTotalBytesRead(shuffleTotalBytesRead: Long): Unit
  def setShuffleLocalBytesRead(shuffleLocalBytesRead: Long): Unit
  def setShuffleRemoteBytesRead(shuffleRemoteBytesRead: Long): Unit
  def setShuffleRemoteBytesReadToDisk(shuffleRemoteBytesReadToDisk: Long): Unit
  def setShuffleBytesWritten(shuffleBytesWritten: Long): Unit
  def setShuffleRecordsWritten(shuffleRecordsWritten: Long): Unit
}