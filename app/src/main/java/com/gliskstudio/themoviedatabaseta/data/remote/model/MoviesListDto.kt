package com.gliskstudio.themoviedatabaseta.data.remote.model

data class MoviesListDto (
    val page: Int,
    val results: List<MovieDto>,
    val totalPages: Int,
    val totalResults: Int
)