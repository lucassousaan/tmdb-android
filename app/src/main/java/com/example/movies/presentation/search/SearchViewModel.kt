package com.example.movies.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.Movie
import com.example.movies.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchUiState {
    data object Idle: SearchUiState
    data object Loading: SearchUiState
    data class Success(val movies: List<Movie>): SearchUiState
    data class Error(val message: String): SearchUiState
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState>  = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchMovies() {
        if (searchQuery.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            repository.searchMovies(_searchQuery.value)
                .onSuccess {
                    _uiState.value = SearchUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = SearchUiState.Error(
                        it.message ?: "Erro desconhecido"
                    )
                }
        }
    }
}
