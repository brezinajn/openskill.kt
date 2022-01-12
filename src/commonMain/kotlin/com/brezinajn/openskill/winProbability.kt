package com.brezinajn.openskill

import com.brezinajn.openskill.util.Getter
import kotlin.math.sqrt

inline fun <T> meanSum(list: List<T>, muGetter: Getter<T, Double>) =
    list.fold(.0) { acc, elem -> acc + elem.let(muGetter) }

inline fun <T> sigmaSqSum(list: List<T>, sigmaGetter: Getter<T, Double>) = list.fold(.0) { acc, elem ->
    val sigma = elem.let(sigmaGetter)
    acc + (sigma * sigma)
}


internal inline fun <PLAYER> Constants.winProbability(
    a: List<PLAYER>,
    b: List<PLAYER>,
    muGetter: Getter<PLAYER, Double>,
    sigmaGetter: Getter<PLAYER, Double>,
) =
    (meanSum(a, muGetter) - meanSum(b, muGetter)) / sqrt(
        (a.size + b.size) * BETA_SQ + sigmaSqSum(a, sigmaGetter) + sigmaSqSum(b, sigmaGetter)
    )