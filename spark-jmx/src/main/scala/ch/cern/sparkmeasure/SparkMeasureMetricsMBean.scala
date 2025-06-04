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
}
