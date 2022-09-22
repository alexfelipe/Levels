package br.com.alura.levels

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import br.com.alura.levels.dao.GamesDao
import br.com.alura.levels.dao.UsersDao
import br.com.alura.levels.model.User
import br.com.alura.levels.sampleData.sampleGames
import br.com.alura.levels.sampleData.sampleUsers
import br.com.alura.levels.ui.screens.*
import br.com.alura.levels.ui.theme.LevelsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val screens = listOf(
                        Screen.GamesList,
                        Screen.FavoriteGames,
                        Screen.Profile
                    )
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            val navBackStackEntry by
                            navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            val isBottomNavigationItem =
                                currentDestination?.route?.let { route ->
                                    screens.firstOrNull {
                                        it.route == route
                                    } != null
                                } ?: false
                            if (isBottomNavigationItem) {
                                BottomNavigation {
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
                        }
                    ) {
                        val gamesDao = remember {
                            GamesDao(sampleGames)
                        }
                        val userDao = remember {
                            UsersDao()
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Screen.SignIn.route,
                            Modifier.padding(it)
                        ) {
                            composable(Screen.GamesList.route) {
                                GamesListScreen(gamesDao)
                            }
                            composable(Screen.FavoriteGames.route) {
                                FavoriteGames(gamesDao)
                            }
                            composable(
                                "${Screen.Profile.route}?nickname={nickname}",
                                arguments = listOf(navArgument("nickname") { nullable = true })
                            ) { backStackEntry ->
                                backStackEntry.arguments?.getString("nickname")?.let { nickname ->
                                    val user: User = userDao
                                        .findUserByNickname(nickname) ?: sampleUsers.first()
                                    ProfileScreen(
                                        user
                                    )
                                }
                            }
                            composable(Screen.SignIn.route) {
                                SignInScreen(
                                    onSignInClick = { nickname ->
                                        navController.navigate("${Screen.Profile.route}?nickname=$nickname")
                                    },
                                    onSignUpClick = {
                                        navController.navigate(Screen.SignUp.route)
                                    }
                                )
                            }
                            composable(Screen.SignUp.route) {
                                SignUpScreen(onSignUpClick = {
                                    navController.popBackStack()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
