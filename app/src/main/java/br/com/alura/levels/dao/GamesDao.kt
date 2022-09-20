package br.com.alura.levels.dao

import br.com.alura.levels.model.Game
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GamesDao(
    games: List<Game> = emptyList(),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    init {
        Companion.games.addAll(games)
        CoroutineScope(dispatcher).launch {
            gamesFlow.emit(games)
        }
    }

    companion object {
        private val games: MutableList<Game> = mutableListOf()
        private val gamesFlow = MutableStateFlow<List<Game>>(emptyList())
    }

    fun games(): Flow<List<Game>> = gamesFlow

    fun favoriteGames(): Flow<List<Game>> = gamesFlow
        .map {
            it.filter { game ->
                game.favorited
            }
        }

    suspend fun save(game: Game) {
        games.indexOfFirst {
            game.id == it.id
        }.let { index ->
            if (index == -1) {
                games.add(game)
            } else {
                games[index] = game
            }
        }
        gamesFlow.emit(listOf(*games.toTypedArray()))
    }

}