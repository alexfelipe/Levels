package br.com.alura.levels.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector? = null,
    val label: String = ""
) {
    object GamesList : Screen(
        route = "gamesList",
        icon = Icons.Default.Gamepad,
        label = "Games list"
    )

    object FavoriteGames : Screen(
        route = "favoriteGames",
        icon = Icons.Default.Favorite,
        label = "Favorite games"
    )

    object Profile : Screen(
        route = "profile",
        icon = Icons.Default.Person,
        label = "Profile"
    )
    object SignIn : Screen(route = "signIn")
    object SignUp : Screen(route = "signUp")
}
