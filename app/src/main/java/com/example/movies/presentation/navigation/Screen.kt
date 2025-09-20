package com.example.movies.presentation.navigation

import androidx.annotation.StringRes
import com.example.movies.R

sealed class Screen(val route: String, @StringRes val titleResId: Int) {
    data object Movies: Screen("movies", R.string.movies)
    data object Search: Screen("search", R.string.search)
    data object Favorites: Screen("favorites", R.string.favorites)
}
