package com.brezinajn.openskill

interface GameTC<TEAM, PLAYER> : TeamTC<TEAM, PLAYER> {
    val List<TEAM>.rank: List<Int>
}

interface TeamTC<TEAM, PLAYER> : PlayerTC<PLAYER> {
    val TEAM.players: List<PLAYER>
}

interface PlayerTC<PLAYER> {
    val PLAYER.mu: Double
    val PLAYER.sigma: Double

    val PLAYER.sigmaSq
        get() = sigma * sigma
}