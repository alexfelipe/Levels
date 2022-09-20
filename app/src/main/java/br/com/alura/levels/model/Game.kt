package br.com.alura.levels.model

import java.util.*

data class Game(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val genre: String,
    val favorited: Boolean = false
)
