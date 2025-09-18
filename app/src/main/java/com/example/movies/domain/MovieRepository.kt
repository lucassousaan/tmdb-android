package com.example.movies.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Result<List<Movie>>

    suspend fun getTopRatedMovies(): Result<List<Movie>>

    suspend fun getUpcomingMovies(): Result<List<Movie>>

    suspend fun getTrendingTodayMovies(): Result<List<Movie>>

    suspend fun searchMovies(query: String): Result<List<Movie>>

    suspend fun favoriteMovie(movie: Movie)

    suspend fun unfavoriteMovie(movie: Movie)

    fun getFavoritedMovies(): Flow<List<Movie>>

    suspend fun isMovieFavorited(movieId: Int): Boolean

    suspend fun getMovieDetails(movieId: Int): Result<Movie>

}