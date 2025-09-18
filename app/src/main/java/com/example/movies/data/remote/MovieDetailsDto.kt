package com.example.movies.data.remote

import com.google.gson.annotations.SerializedName

data class GenreDto(
    val name: String
)

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val runtime: Int?,
    val genres: List<GenreDto>
)
