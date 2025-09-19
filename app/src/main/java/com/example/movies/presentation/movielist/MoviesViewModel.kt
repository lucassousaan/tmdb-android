package com.example.movies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.Movie
import com.example.movies.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CategoryState {
    data object Loading : CategoryState
    data class Success(val movies: List<Movie>) : CategoryState
    data class Error(val message: String) : CategoryState
}

data class MovieListData(
    val popularMoviesState: CategoryState = CategoryState.Loading,
    val topRatedMoviesState: CategoryState = CategoryState.Loading,
    val trendingTodayMoviesState: CategoryState = CategoryState.Loading,
    val upcomingMoviesState: CategoryState = CategoryState.Loading
)

sealed interface MovieListUiState {
    data object Loading : MovieListUiState
    data class Success(val data: MovieListData) : MovieListUiState
    data class Error(val message: String) : MovieListUiState
}

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<MovieListUiState>(MovieListUiState.Success(MovieListData()))
    val uiState: StateFlow<MovieListUiState> = _uiState

    init {
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchTrendingTodayMovies()
        fetchUpcomingMovies()
    }

    private fun fetchPopularMovies() = fetchMovies(
        fetch = { repository.getPopularMovies() },
        updateState = { data, state -> data.copy(popularMoviesState = state) }
    )

    private fun fetchTopRatedMovies() = fetchMovies(
        fetch = { repository.getTopRatedMovies() },
        updateState = { data, state -> data.copy(topRatedMoviesState = state) }
    )

    private fun fetchTrendingTodayMovies() = fetchMovies(
        fetch = { repository.getTrendingTodayMovies() },
        updateState = { data, state -> data.copy(trendingTodayMoviesState = state) }
    )

    private fun fetchUpcomingMovies() = fetchMovies(
        fetch = { repository.getUpcomingMovies() },
        updateState = { data, state -> data.copy(upcomingMoviesState = state) }
    )

    private fun fetchMovies(
        fetch: suspend () -> Result<List<Movie>>,
        updateState: (MovieListData, CategoryState) -> MovieListData
    ) {
        viewModelScope.launch {
            fetch()
                .onSuccess { movies ->
                    _uiState.update {
                        if (it is MovieListUiState.Success) {
                            it.copy(data = updateState(it.data, CategoryState.Success(movies)))
                        } else it
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        if (it is MovieListUiState.Success) {
                            it.copy(
                                data = updateState(
                                    it.data,
                                    CategoryState.Error(error.message ?: "Erro")
                                )
                            )
                        } else it
                    }
                }
        }
    }

}