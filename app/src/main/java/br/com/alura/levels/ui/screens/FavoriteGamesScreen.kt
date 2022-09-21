package br.com.alura.levels.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.levels.dao.GamesDao
import br.com.alura.levels.sampleData.sampleGames
import br.com.alura.levels.ui.theme.LevelsTheme
import kotlinx.coroutines.launch

@Composable
fun FavoriteGames(dao: GamesDao) {
    val games by dao.favoriteGames().collectAsState(emptyList())
    val scope = rememberCoroutineScope()
    LazyColumn {
        items(games) { game ->
            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .heightIn(100.dp)
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(text = game.name)
                    Text(text = game.genre)
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        Modifier.pointerInput(game) {
                            detectTapGestures(
                                onTap = {
                                    scope.launch {
                                        dao.save(game.copy(favorited = !game.favorited))
                                    }
                                }
                            )
                        },
                        tint = Color(0xFFfdd835)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun FavoriteGamesPreview() {
    LevelsTheme {
        Surface {
            FavoriteGames(GamesDao(sampleGames))
        }
    }
}