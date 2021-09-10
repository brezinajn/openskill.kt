package com.brezinajn.openskill

import kotlin.math.exp
import kotlin.math.sqrt

internal fun <TEAM, PLAYER> TeamTC<TEAM, PLAYER>.teamRating(teams: List<TEAM>): List<TeamRating<TEAM>> =
    teams.mapIndexed { index, team ->
        TeamRating(
            mu = team.players.sumOf { it.mu },
            sigmaSq = team.players.sumOf { it.sigmaSq },
            team = team,
            rank = index
        )
    }

internal data class TeamRating<TEAM>(
    val mu: Double,
    val sigmaSq: Double,
    val team: TEAM,
    val rank: Int
)

internal fun <TEAM> utilC(teamRatings: List<TeamRating<TEAM>>) = sqrt(teamRatings.sumOf { it.sigmaSq + BETA_SQ })


internal fun <TEAM> utilSumQ(teamRatings: List<TeamRating<TEAM>>, c: Double): List<Double> =
    teamRatings.map { (_, _, _, qRank) ->
        teamRatings.filter { (_, _, _, iRank) -> iRank >= qRank }
            .sumByDouble { (iMu, _, _, _) -> exp(iMu / c) }
    }
        .fold(mutableListOf(), ::intoRankHash)


private fun <T> intoRankHash(
    acc: MutableList<T>,
    it: T
): MutableList<T> {
    acc.add(it)
    return acc
}

internal fun <TEAM> utilA(teamRatings: List<TeamRating<TEAM>>): List<Int> =
    teamRatings.map { (_, _, _, iRank) ->
        teamRatings.filter { (_, _, _, qRank) -> iRank == qRank }.size
    }
        .fold(mutableListOf(), ::intoRankHash)
