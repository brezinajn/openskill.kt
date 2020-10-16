package com.brezinajn.openskill.util

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sqrt

object Gaussian {
    // return pdf(x) = standard Gaussian pdf
    fun pdf(x: Double): Double = exp(-x * x / 2) / sqrt(2 * PI)

    // return pdf(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
    fun pdf(x: Double, mu: Double, sigma: Double): Double = pdf((x - mu) / sigma) / sigma


    fun cdf(z: Double): Double = when {
        z < -8.0 -> .0
        z > 8.0 -> 1.0
        else -> {
            var sum = 0.0
            var term = z
            var i = 3
            while (sum + term != sum) {
                sum += term
                term = term * z * z / i
                i += 2
            }
            0.5 + sum * pdf(z)
        }
    }

//    }

    // return cdf(z, mu, sigma) = Gaussian cdf with mean mu and stddev sigma
    fun cdf(z: Double, mu: Double, sigma: Double): Double = cdf((z - mu) / sigma)

    // Compute z such that cdf(z) = y via bisection search
    fun inverseCDF(y: Double): Double = inverseCDF(y, 0.00000001, -8.0, 8.0)

    private tailrec fun inverseCDF(y: Double, delta: Double, lo: Double, hi: Double): Double {
        val mid = lo + (hi - lo) / 2
        return when {
            hi - lo < delta -> mid
            cdf(mid) > y -> inverseCDF(y, delta, lo, mid)
            else -> inverseCDF(y, delta, mid, hi)
        }
    }
}