package com.example.movies.data.repository

import com.example.movies.data.remote.MovieDto
import com.example.movies.data.remote.TmdbApiService
import com.example.movies.domain.Movie
import com.example.movies.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
): MovieRepository {
    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return try {
            val response = apiService.getPopularMovies()
            val movies = response.results.map { it.toDomain() }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTopRatedMovies(): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingTodayMovies(): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun favoriteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun unfavoriteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun getFavoritedMovies(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun isMovieFavorited(movieId: Int): Boolean {
        TODO("Not yet implemented")
    }
}

private fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = "https://image.tmdb.org/t/p/w500${this.posterPath}",
        voteAverage = this.voteAverage
    )
}