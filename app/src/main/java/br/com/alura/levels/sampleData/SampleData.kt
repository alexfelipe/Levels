package br.com.alura.levels.sampleData

import br.com.alura.levels.model.Game
import br.com.alura.levels.model.User

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

val sampleUsers = listOf(
    User(
        nickname = "alexfelipe",
        avatar = "https://cdn.epicstream.com/assets/uploads/ckeditor/images/1634102916_gojo.jpg"
    ),
    User(
        nickname = "kako",
        avatar = "https://www.hypeness.com.br/1/2015/04/sapo-caco3.jpg"
    )
)