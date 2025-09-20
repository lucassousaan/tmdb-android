package com.example.movies.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movies.presentation.favorites.FavoritesScreen
import com.example.movies.presentation.movielist.MovieListScreen
import com.example.movies.presentation.navigation.Screen
import com.example.movies.presentation.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val screens = listOf(
        Screen.Movies,
        Screen.Search,
        Screen.Favorites,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = screens.find {
        it.route == currentDestination?.route
    } ?: Screen.Movies

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = currentScreen.titleResId)) }
            )
        },
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            when (screen) {
                                Screen.Movies -> Icon(Icons.Filled.Home, contentDescription = null)
                                Screen.Search -> Icon(Icons.Filled.Search, contentDescription = null)
                                else -> Icon(Icons.Filled.Favorite, contentDescription = null)
                            }
                        },
                        label = { Text(stringResource(id = screen.titleResId)) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Movies.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Movies.route) { MovieListScreen() }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Favorites.route) { FavoritesScreen() }
        }
    }
}