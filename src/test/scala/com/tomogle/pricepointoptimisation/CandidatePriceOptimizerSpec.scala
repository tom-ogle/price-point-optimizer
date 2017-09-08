package com.tomogle.pricepointoptimisation

import org.scalatest.FlatSpec
import org.scalatest.Matchers

/**
  *
  */
class CandidatePriceOptimizerSpec extends FlatSpec with Matchers {

  behavior of "optimizePrice"

  it should "Identify the price closest to the original price" in new CandidatePriceOptimizer {
    val originalPrice = 6890
    val candidatePrices = Set(
      6893, 6903,
      6885, 6895, 6905,
      6889, 6899
    )
    val expectedBestPricePoint = Some(6889)

    val actualResult = optimizePrice(originalPrice, candidatePrices)
    actualResult shouldBe expectedBestPricePoint

  }

  behavior of "newCandidateIsCloser"

  it should "Give the new candidate when a new candidate is closer" in new CandidatePriceOptimizer {
    val originalPrice = 6890
    val bestSoFar = Option(1)
    val newCandidate = 6893

    val expectedResult = Some(newCandidate)
    val actualResult = findClosest(originalPrice)(bestSoFar, newCandidate)

    actualResult shouldBe expectedResult
  }

  it should "Give the best so far when a new candidate is less close" in new CandidatePriceOptimizer {
    val originalPrice = 6890
    val bestSoFar = Option(6892)
    val newCandidate = 6893

    val expectedResult = bestSoFar
    val actualResult = findClosest(originalPrice)(bestSoFar, newCandidate)

    actualResult shouldBe expectedResult
  }

  it should "Give the best so far when a new candidate is equally close" in new CandidatePriceOptimizer {
    val originalPrice = 6890
    val bestSoFar = Option(6891)
    val newCandidate = 6889

    val expectedResult = bestSoFar
    val actualResult = findClosest(originalPrice)(bestSoFar, newCandidate)

    actualResult shouldBe expectedResult
  }
}
