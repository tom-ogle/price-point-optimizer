package com.tomogle.pricepointoptimisation

import com.holdenkarau.spark.testing.RDDComparisons
import com.holdenkarau.spark.testing.SharedSparkContext
import org.apache.spark.rdd.RDD
import org.scalatest.FlatSpec

/**
  *
  */
class PricePointOptimizerAcceptanceSpec extends FlatSpec with SharedSparkContext with RDDComparisons {

  behavior of "PricePointOptimizer"
  // TODO: Consider switching to given, when, then format

  it should "fulfil the acceptance criteria" in new PricePointOptimizer {
    val inputProducts = sc.parallelize(Seq(
      ("A", 6890, 6885, 6905),
      ("B", 7197, 7192, 7198),
      ("C", 6997, 6996, 6998),
      ("D", 6854, 6854, 6854),
      ("E", 6897, 6885, 6905)
    ))

    val inputPricePoints = Set(3, 5, 9)

    val expectedOutput = sc.parallelize(Seq(
      ("A", 6890, 6885, 6905, Some(6889)),
      ("B", 7197, 7192, 7198, Some(7195)),
      ("C", 6997, 6996, 6998, None),
      ("D", 6854, 6854, 6854, None),
      ("E", 6897, 6885, 6905, Some(6895))
    ))

    val actualOutput = findOptimalPricePoints(inputPricePoints, inputProducts)
    actualOutput.foreach(println)
    assertRDDEquals(expectedOutput, actualOutput)
  }

}
