package br.com.alura.levels.sampleData

import br.com.alura.levels.model.Game

val sampleGames = listOf(
    Game(
        name = "Gow of War",
        genre = "RPG"
    ),
    Game(
        name = "FIFA",
        genre = "e-sports",
        favorited = true
    ),
    Game(
        name = "NFS",
        genre = "Corrida"
    ),
    Game(
        name = "Overwatch",
        genre = "FPS",
        favorited = true
    )
)

val sampleFavoriteGames = sampleGames.filter(Game::favorited)