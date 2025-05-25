package com.gliskstudio.themoviedatabaseta.model

data class MovieItem(
    val id: Int,
    val movieTitle: String,
    val description: String,
    val imageUrl: String,
    val rating: String,
    val releaseDate: String,
    var isLiked: Boolean = false, // TODO
    var isDownloaded: Boolean = false // TODO
)