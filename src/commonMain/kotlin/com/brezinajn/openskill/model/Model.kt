package com.brezinajn.openskill.model

import com.brezinajn.openskill.Constants
import com.brezinajn.openskill.TeamTC
import com.brezinajn.openskill.util.Setter
import kotlin.math.max
import kotlin.math.sqrt


interface Model<TEAM, PLAYER> : TeamTC<TEAM, PLAYER>, Constants {

    val sigmaSetter: Setter<PLAYER, Double>
    val muSetter: Setter<PLAYER, Double>
    val playersSetter: Setter<TEAM, List<PLAYER>>


    private fun PLAYER.setNewRating(iSigmaSq: Double, iOmega: Double, iDelta: Double): PLAYER =
        muSetter(this, mu + sigmaSq / iSigmaSq * iOmega)
            .let {
                sigmaSetter(it, sigma * sqrt(max(1 - sigmaSq / iSigmaSq * iDelta, EPSILON)))
            }

    fun TEAM.setPlayers(iSigmaSq: Double, iOmega: Double, iDelta: Double) = players.map {
        it.setNewRating(
            iSigmaSq = iSigmaSq,
            iDelta = iDelta,
            iOmega = iOmega
        )
    }.let { playersSetter(this, it) }

    operator fun invoke(game: List<TEAM>): List<TEAM>
}