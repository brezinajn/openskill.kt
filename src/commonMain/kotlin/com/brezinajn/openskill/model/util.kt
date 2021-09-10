package com.brezinajn.openskill.model

import com.brezinajn.openskill.Constants
import kotlin.math.sqrt

internal val Constants.TWOBETASQ
    get() = 2 * BETA_SQ


internal fun gamma(ciq: Double, sigmaSq: Double): Double = sqrt(sigmaSq) / ciq