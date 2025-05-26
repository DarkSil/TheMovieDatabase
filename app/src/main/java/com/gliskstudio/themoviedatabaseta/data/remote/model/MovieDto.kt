package com.gliskstudio.themoviedatabaseta.data.remote.model

import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem

data class MovieDto(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
) {
    fun toItem(): MovieItem {
        return MovieItem(
            id,
            title,
            overview,
            poster_path,
            voteToRating(),
            release_date
        )
    }

    private fun voteToRating(): String {
        val roundedRating = Math.round(vote_average*10).toFloat()/10f
        return "$roundedRating/10"
    }
}