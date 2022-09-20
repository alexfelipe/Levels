package br.com.alura.levels.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GamesListScreen(modifier: Modifier = Modifier) {
    Text(text = "Home Screen")
}

@Preview
@Composable
fun GamesListPreview() {
    GamesListScreen()
}

