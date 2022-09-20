package br.com.alura.levels.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.levels.dao.GamesDao
import br.com.alura.levels.sampleData.sampleGames
import kotlinx.coroutines.launch

@Composable
fun GamesListScreen(dao: GamesDao) {
    val myGames by dao.games().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    Log.i("GamesListScreen", "GamesListScreen: $myGames")
    LazyColumn {
        items(myGames) { game ->
            Card(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .heightIn(100.dp)
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
                        Modifier.clickable {
                            scope.launch {
                                dao.save(game.copy(favorited = !game.favorited))
                            }
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
    GamesListScreen(GamesDao(sampleGames))
}

