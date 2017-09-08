name := "price-point-optimization"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "1.6.3"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "1.6.3"
libraryDependencies += "org.apache.spark" % "spark-hive_2.11" % "1.6.3"
libraryDependencies += "com.holdenkarau" % "spark-testing-base_2.11" % "1.6.3_0.6.0" % "test"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test"
    