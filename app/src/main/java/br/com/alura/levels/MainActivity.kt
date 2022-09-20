package br.com.alura.levels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.levels.dao.GamesDao
import br.com.alura.levels.model.User
import br.com.alura.levels.sampleData.sampleGames
import br.com.alura.levels.ui.screens.*
import br.com.alura.levels.ui.theme.LevelsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelsTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val screens = listOf(
                        Screen.GamesList,
                        Screen.FavoriteGames,
                        Screen.Profile
                    )
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigation {
                                val navBackStackEntry by
                                navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                screens.forEach { screen ->
                                    BottomNavigationItem(
                                        selected = currentDestination?.hierarchy?.any {
                                            it.route == screen.route
                                        } == true,
                                        label = {
                                            Text(text = screen.label)
                                        },
                                        icon = {
                                            screen.icon?.let {
                                                Icon(it, contentDescription = null)
                                            }
                                        },
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        })
                                }
                            }
                        }
                    ) {
                        val dao = remember {
                            GamesDao(sampleGames)
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Screen.GamesList.route,
                            Modifier.padding(it)
                        ) {
                            composable(Screen.GamesList.route) {
                                GamesListScreen(dao)
                            }
                            composable(Screen.FavoriteGames.route) {
                                FavoriteGames(dao)
                            }
                            composable(Screen.Profile.route) {
                                ProfileScreen(
                                    User(
                                        nickname = "alexfelipe",
                                        avatar = "https://i.pinimg.com/474x/9f/b2/75/9fb275c854b7edd12e57a8a0113b2b70.jpg"
                                    )
                                )
                            }
                            composable(Screen.SignIn.route) {
                                SignInScreen()
                            }
                            composable(Screen.SignUp.route) {
                                SignUpScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
