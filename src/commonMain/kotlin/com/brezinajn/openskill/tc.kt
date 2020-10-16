package com.brezinajn.openskill

interface TeamTC<TEAM, PLAYER> : PlayerTC<PLAYER> {
    val TEAM.players: List<PLAYER>
}

interface PlayerTC<PLAYER> {
    val PLAYER.mu: Double
    val PLAYER.sigma: Double

    val PLAYER.sigmaSq
        get() = sigma * sigma
}