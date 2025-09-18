package com.example.movies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.Movie
import com.example.movies.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MovieListUiState {
    data object Loading: MovieListUiState
    data class Success(val movies: List<Movie>): MovieListUiState
    data class Error(val message: String): MovieListUiState
}

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading

            repository.getPopularMovies()
                .onSuccess {
                    _uiState.value = MovieListUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = MovieListUiState.Error(it.message ?: "Erro desconhecido")
                }
        }
    }
}