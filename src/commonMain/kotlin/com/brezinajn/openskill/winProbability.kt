package com.brezinajn.openskill

import kotlin.math.sqrt

private fun <T> meanSum(list: List<T>, muGetter: (T) -> Double) =
    list.fold(.0) { acc, elem -> acc + elem.let(muGetter) }

private fun <T> sigmaSqSum(list: List<T>, sigmaGetter: (T) -> Double) = list.fold(.0) { acc, elem ->
    val sigma = elem.let(sigmaGetter)
    acc + (sigma * sigma)
}

fun <T> winProbability(a: List<T>, b: List<T>, muGetter: (T) -> Double, sigmaGetter: (T) -> Double) =
    (meanSum(a, muGetter) - meanSum(b, muGetter)) / sqrt(
        (a.size + b.size) * BETA_SQ + sigmaSqSum(a, sigmaGetter) + sigmaSqSum(b, sigmaGetter)
    )