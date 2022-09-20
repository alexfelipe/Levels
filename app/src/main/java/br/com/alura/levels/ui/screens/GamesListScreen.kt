package br.com.alura.levels.ui.screens

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
fun GamesListScreen(dao: GamesDao) {
    val games by dao.games().collectAsState(emptyList())
    val scope = rememberCoroutineScope()
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(games) { game ->
            Log.i("GamesList", "GamesListScreen: $game")
            Surface(
                Modifier
                    .fillMaxWidth()
                    .heightIn(100.dp),
                shape = RoundedCornerShape(10)
            ) {
                Column(Modifier.padding(8.dp)) {
                    Text(text = game.name)
                    Text(text = game.genre)
                    val iconTint = if (game.favorited) {
                        Color(0xFFfdd835)
                    } else {
                        Color(0xFF37474f)
                    }
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        Modifier
                            .size(36.dp)
                            .pointerInput(game) {
                                detectTapGestures(onTap = {
                                    scope.launch {
                                        dao.save(game.copy(favorited = !game.favorited))
                                    }
                                })
                            },
                        tint = iconTint
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamesListPreview() {
    LevelsTheme {
        Surface(color = MaterialTheme.colors.background) {
            GamesListScreen(GamesDao(sampleGames))
        }
    }
}

