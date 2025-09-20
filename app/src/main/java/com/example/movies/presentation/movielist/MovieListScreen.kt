package com.example.movies.presentation.movielist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.movies.R
import com.example.movies.domain.Movie

@Composable
fun MovieListScreen(
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Box {
        when (val state = uiState.value) {
            is MovieListUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is MovieListUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message)
                }
            }
            is MovieListUiState.Success -> {
                MovieList(
                    data = state.data,
                )
            }
        }
    }
}

@Composable
fun MovieList(
    data: MovieListData,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        stringResource(R.string.filmes_populares) to data.popularMoviesState,
        stringResource(R.string.melhores_avaliados) to data.topRatedMoviesState,
        stringResource(R.string.populares_hoje) to data.trendingTodayMoviesState,
        stringResource(R.string.pr_ximos_lan_amentos) to data.upcomingMoviesState,
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { (title, state) ->
            MovieCategoryRow(
                categoryTitle = title,
                categoryState = state
            )
        }
    }
}

@Composable
fun MovieCategoryRow(
    categoryTitle: String,
    categoryState: CategoryState
) {
    Column {
        Text(
            text = categoryTitle,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        when (categoryState) {
            is CategoryState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CategoryState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = categoryState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is CategoryState.Success -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categoryState.movies) {
                        MovieListItem(movie = it)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListItem(
    movie: Movie
) {
    Column(
        modifier = Modifier.width(150.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = stringResource(R.string.poster_do_filme, movie.title),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListItemPreview() {
    MovieListItem(
        movie = Movie(
            id = 1,
            title = "Filme de Exemplo",
            overview = "",
            posterUrl = "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
            voteAverage = 7.5
        )
    )
}
