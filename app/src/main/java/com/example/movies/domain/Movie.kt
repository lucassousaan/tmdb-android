package com.example.movies.domain

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val voteAverage: Double,
    val runtime: Int? = null,
    val genres: List<String> = emptyList()
)
