package com.brezinajn.openskill


interface Constants {
    val Z: Int
    val MU: Double
    val SIGMA: Double
    val BETA: Double
    val BETA_SQ: Double
    val EPSILON: Double


    companion object {
        operator fun invoke(
            Z: Int = 3,
            MU: Double = 25.0,
            SIGMA: Double = MU / Z,
            BETA: Double = SIGMA / 2,
            BETA_SQ: Double = BETA * BETA,
            EPSILON: Double = 0.0001,
        ) = object : Constants {
            override val Z = Z
            override val MU = MU
            override val SIGMA = SIGMA
            override val BETA = BETA
            override val BETA_SQ = BETA_SQ
            override val EPSILON = EPSILON
        }
    }
}