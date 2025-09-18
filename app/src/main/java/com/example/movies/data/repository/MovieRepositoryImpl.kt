package com.example.movies.data.repository

import com.example.movies.data.remote.MovieDetailsDto
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
        return safeApiCall(
            apiCall = { apiService.getPopularMovies() },
            mapper = { response ->
                response.results.map {
                    it.toDomain()
                }
            }
        )
    }

    override suspend fun getTopRatedMovies(): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { apiService.getTopRatedMovies() },
            mapper = { response ->
                response.results.map {
                    it.toDomain()
                }
            }
        )
    }

    override suspend fun getUpcomingMovies(): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { apiService.getUpcomingMovies() },
            mapper = { response ->
                response.results.map {
                    it.toDomain()
                }
            }
        )
    }

    override suspend fun getTrendingTodayMovies(): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { apiService.getTrendingTodayMovies() },
            mapper = { response ->
                response.results.map {
                    it.toDomain()
                }
            }
        )
    }

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        return safeApiCall(
            apiCall = { apiService.searchMovies(query) },
            mapper = { response ->
                response.results.map {
                    it.toDomain()
                }
            }
        )
    }

    override suspend fun getMovieDetails(movieId: Int): Result<Movie> {
        return safeApiCall(
            apiCall = { apiService.getMovieDetails(movieId) },
            mapper = {
                it.toDomain()
            }
        )
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

    private suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> T,
        mapper: (T) -> R
    ): Result<R> {
        return try {
            val response = apiCall()
            val data = mapper(response)
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
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

private fun MovieDetailsDto.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = "https://image.tmdb.org/t/p/w500${this.posterPath}",
        voteAverage = this.voteAverage,
        runtime = this.runtime,
        genres = this.genres.map { it.name }
    )
}