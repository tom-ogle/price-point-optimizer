package com.tomogle.pricepointoptimisation

import org.apache.spark.rdd.RDD

/**
  * We assume:
  *   - that input to the PricePointOptimizer are sanitised
  *   - there is only ever one row per product
  */
trait PricePointOptimizer extends Serializable with CandidatePriceGenerator with CandidatePriceOptimizer {

  // TODO: Converison between Int and Double (watch rounding)

  def findOptimalPricePoints(inputPricePoints: Set[Int], products: RDD[(String, Int, Int, Int)]):
  RDD[(String, Int, Int, Int, Option[Int])] =
    products.map {
      case (productName, originalPrice, minPrice, maxPrice) =>
        val candidatePrices: Set[Int] = generateCandidatesForAllPricePoints(inputPricePoints, minPrice, maxPrice)
        val optimizedPrice: Option[Int] = optimizePrice(originalPrice, candidatePrices)
        (productName, originalPrice, minPrice, maxPrice, optimizedPrice)
    }
}

/**
  * Generates all candidate prices in the given range
  */
trait CandidatePriceGenerator extends Serializable {

  def generateCandidatesForAllPricePoints(pricePoints: Set[Int], minimumPrice: Int, maximumPrice: Int): Set[Int] =
    pricePoints.flatMap { price =>
      generateCandidates(price, minimumPrice, maximumPrice)
    }

  def generateCandidates(pricePoint: Int, minimumPrice: Int, maximumPrice: Int): Set[Int] =
    (minimumPrice to maximumPrice).filter(_.toString.endsWith(pricePoint.toString)).toSet
}

trait CandidatePriceOptimizer extends Serializable {
  def optimizePrice(originalPrice: Int, candidatePrices: Set[Int]): Option[Int] =
    candidatePrices.foldLeft[Option[Int]](None) (findClosest(originalPrice))

  def findClosest(originalPrice: Int)(bestSoFar: Option[Int], newCandidate: Int): Option[Int] = {
    if (bestSoFar.isEmpty) Option(newCandidate)
    else {
      bestSoFar.map(best => {
        val bestDifferenceSoFar = Math.abs(originalPrice - best)
        val newDifference = Math.abs(originalPrice - newCandidate)
        if (newDifference < bestDifferenceSoFar) newCandidate
        else best
      })
    }
  }

}