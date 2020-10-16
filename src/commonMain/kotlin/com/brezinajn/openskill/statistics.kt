package com.brezinajn.openskill

import com.brezinajn.openskill.util.Gaussian
import kotlin.math.abs

const val MIN_VALUE = EPSILON / 10


private fun phiMajor(x: Double) = Gaussian.cdf(x)
private fun phiMinor(x: Double) = Gaussian.pdf(x)

internal fun v(x: Double, t: Double): Double {
    val xt = x - t
    val denom = phiMajor(xt)
    return if (denom < EPSILON) -xt else phiMinor(xt) / denom
}

internal fun w(x: Double, t: Double): Double {
    val xt = x - t
    val denom = phiMajor(xt)

    return if (denom < EPSILON) {
        if (x < 0) 1.0 else .0
    } else v(x, t) * (v(x, t) + xt)
}

internal fun vt(x: Double, t: Double): Double {
    val xx = abs(x)
    val b = phiMajor(t - xx) - phiMajor(-t - xx)
    return if (b < MIN_VALUE) {
        if (x < 0) -x - t
        -x + t
    } else {
        val a = phiMinor(-t - xx) - phiMinor(t - xx)
        val result = if (x < 0) -a else a
        result / b
    }
}

internal fun wt(x: Double, t: Double): Double {
    val xx = abs(x)
    val b = phiMajor(t - xx) - phiMajor(-t - xx)
    return if (b < MIN_VALUE) 1.0
    else ((t - xx) * phiMinor(t - xx) + (t + xx) * phiMinor(-t - xx)) / b + vt(x, t) * vt(x, t)
}
