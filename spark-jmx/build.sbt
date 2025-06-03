name := "sparkmeasure-jmx"

version := "0.1.0"

scalaVersion := "2.12.18" // Compatible avec Spark 3.x builds

ThisBuild / organization := "ch.cern"

ThisBuild / javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
ThisBuild / scalacOptions ++= Seq("-deprecation", "-feature")

// Spark dependency provided at runtime (e.g. via spark-submit)
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.0" % "provided",
  "org.apache.spark" %% "spark-sql"  % "3.5.0" % "provided"
)

