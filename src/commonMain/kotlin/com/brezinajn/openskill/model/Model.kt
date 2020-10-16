package com.brezinajn.openskill.model

import com.brezinajn.openskill.EPSILON
import com.brezinajn.openskill.TeamTC
import kotlin.math.max
import kotlin.math.sqrt


interface Model<TEAM, PLAYER> : TeamTC<TEAM, PLAYER> {

    val sigmaSetter: (PLAYER, Double) -> PLAYER
    val muSetter: (PLAYER, Double) -> PLAYER
    val playersSetter: (TEAM, List<PLAYER>) -> TEAM


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