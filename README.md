Kotlin multiplatform implementation of [this](https://github.com/philihp/openskill.js) great library, that can be used with your model out of the box!

Usage:
```kotlin
// Your player class
data class Player(
    val mu: Double,
    val sigma: Double,
    // …
)

// Your team class
data class Team(
    val players: List<Player>,
    // …
)

// Initialization of the model for your classes
val model = BradleyTerryFull(
    sigmaGetter = Player::sigma,
    muGetter = Player::mu,
    playersGetter = Team::players,
    sigmaSetter = {player, sigma -> player.copy(sigma = sigma)},
    muSetter = {player, mu -> player.copy(mu = mu)},
    playersSetter = {team, players -> team.copy(players = players)}
)

// Match result - sorted descending from the first place
val teams: List<Team>

// Match result with updated player ratings
val teamsWithUpdatedRatings = model(teams)
```