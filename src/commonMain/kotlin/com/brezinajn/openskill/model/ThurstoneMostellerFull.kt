package com.brezinajn.openskill.model

import com.brezinajn.openskill.*
import com.brezinajn.openskill.util.Getter
import com.brezinajn.openskill.util.Setter
import kotlin.math.sqrt

interface ThurstoneMostellerFull<TEAM, PLAYER> : Model<TEAM, PLAYER> {
    override fun invoke(game: List<TEAM>): List<TEAM> {
        val teamRatings = teamRating(game)

        return teamRatings.map { (iMu, iSigmaSq, iTeam, iRank) ->
            val (iOmega, iDelta) = teamRatings.filter { (_, _, _, qRank) -> qRank != iRank }
                .fold(.0 to .0) { (omega, delta), (qMu, qSigmaSq, _, qRank) ->
                    val ciq = sqrt(iSigmaSq + qSigmaSq + TWOBETASQ)
                    val deltaMu = (iMu - qMu) / ciq
                    val sigSqToCiq = iSigmaSq / ciq
                    val gamma = gamma(ciq, teamRatings.size.toDouble())

                    if (qRank == iRank)
                        Pair(
                            omega + sigSqToCiq * vt(deltaMu, EPSILON / ciq),
                            delta + ((gamma * sigSqToCiq) / ciq) * wt(deltaMu, EPSILON / ciq)
                        )
                    else {
                        val sign = if (qRank > iRank) 1 else -1
                        Pair(
                            omega + sign * sigSqToCiq * v(sign * deltaMu, EPSILON / ciq),
                            delta + ((gamma * sigSqToCiq) / ciq) * w(sign * deltaMu, EPSILON / ciq)
                        )
                    }
                }
            iTeam.setPlayers(
                iSigmaSq = iSigmaSq,
                iDelta = iDelta,
                iOmega = iOmega
            )
        }
    }

    companion object {
        operator fun <TEAM, PLAYER> invoke(
            sigmaSetter: Setter<PLAYER, Double>,
            sigmaGetter: Getter<PLAYER, Double>,
            muSetter: Setter<PLAYER, Double>,
            muGetter: Getter<PLAYER, Double>,
            playersSetter: Setter<TEAM, List<PLAYER>>,
            playersGetter: Getter<TEAM, List<PLAYER>>,
            rankGetter: Getter<List<TEAM>, List<Int>> = Getter { it.indices.toList() },
            constants: Constants,
            gamma: (Double, Double) -> Double = { _, k -> 1 / k },
        ): ThurstoneMostellerFull<TEAM, PLAYER> =
            object : ThurstoneMostellerFull<TEAM, PLAYER>, Constants by constants {
                override val sigmaSetter = sigmaSetter
                override val muSetter = muSetter
                override val playersSetter = playersSetter

                override val gamma: (Double, Double) -> Double = gamma
                override val TEAM.players: List<PLAYER>
                    get() = playersGetter(this)
                override val PLAYER.mu: Double
                    get() = muGetter(this)
                override val PLAYER.sigma: Double
                    get() = sigmaGetter(this)
                override val List<TEAM>.rank: List<Int>
                    get() = rankGetter(this)
            }
    }
}