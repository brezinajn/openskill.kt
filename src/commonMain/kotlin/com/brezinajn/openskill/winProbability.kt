package com.brezinajn.openskill

import com.brezinajn.openskill.util.Getter
import kotlin.math.sqrt

private fun <T> meanSum(list: List<T>, muGetter: Getter<T, Double>) =
    list.fold(.0) { acc, elem -> acc + elem.let(muGetter::invoke) }

private fun <T> sigmaSqSum(list: List<T>, sigmaGetter: Getter<T, Double>) = list.fold(.0) { acc, elem ->
    val sigma = elem.let(sigmaGetter::invoke)
    acc + (sigma * sigma)
}

fun <T> winProbability(a: List<T>, b: List<T>, muGetter: Getter<T, Double>, sigmaGetter: Getter<T, Double>) =
    (meanSum(a, muGetter) - meanSum(b, muGetter)) / sqrt(
        (a.size + b.size) * BETA_SQ + sigmaSqSum(a, sigmaGetter) + sigmaSqSum(b, sigmaGetter)
    )