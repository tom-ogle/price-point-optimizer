package com.tomogle.pricepointoptimisation

import org.scalatest.FlatSpec
import org.scalatest.Matchers

/**
  *
  */
class CandidatePriceGeneratorSpec extends FlatSpec with Matchers {

  // TODO: Case for when there isn't a result
  // TODO: Corner cases

  behavior of "generateCandidates for a single price point"

  it should "generate a single candidate prices within the range 0 to 10" in new CandidatePriceGenerator {
    val minimumPrice = 6885
    val maximumPrice = 6905
    val pricePoint = 3

    val expectedCandidatePrices = Set(6893, 6903)

    val actualCandidatePrices = generateCandidates(pricePoint, minimumPrice, maximumPrice)
    actualCandidatePrices shouldBe expectedCandidatePrices
  }

  behavior of "generateCandidatesForAllPricePoints"

  it should "generate candidates for all price points" in new CandidatePriceGenerator {
    val minimumPrice = 6885
    val maximumPrice = 6905
    val pricePoints = Set(3, 5, 9)

    val expectedCandidatePrices = Set(
      6893, 6903,
      6885, 6895, 6905,
      6889, 6899
    )
    val actualCandidatePrices = generateCandidatesForAllPricePoints(pricePoints, minimumPrice, maximumPrice)
    actualCandidatePrices shouldBe expectedCandidatePrices
  }

}
