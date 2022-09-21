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
import androidx.navigation.compose.*
import br.com.alura.levels.dao.GamesDao
import br.com.alura.levels.model.User
import br.com.alura.levels.sampleData.sampleGames
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
                        val dao = remember {
                            GamesDao(sampleGames)
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Screen.SignIn.route,
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
                                        avatar = "https://1.bp.blogspot.com/-pcFln_2fDmU/YVHSERlXM3I/AAAAAAAAB_Y/tGzfezDKTGogykVhnYGML6EBhGiuldxzACLcBGAsYHQ/s1280/O%2BFilme%2Bde%2BJujutsu%2BKaisen%2B0%2Brevelou%2Bo%2Bdesign%2Bde%2BSatoru%2BGojo%2BMANUAL%2BDO%2BOTAKU.webp"
                                    ),
                                    onLeaveClick = {
                                        navController.navigate(Screen.SignIn.route)
                                    }
                                )
                            }
                            composable(Screen.SignIn.route) {
                                SignInScreen(
                                    onSignInClick = {
                                        navController.navigate(Screen.GamesList.route)
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
