package com.gliskstudio.themoviedatabaseta.model

import com.gliskstudio.themoviedatabaseta.R

sealed class CategoryType(
    open val isLimited: Boolean,
    val categoryTitleId: Int
) {

    data class Featured(override val isLimited: Boolean = false) : CategoryType(
        isLimited,
        R.string.featured_movies
    )
    data class Liked(override val isLimited: Boolean = false) : CategoryType(
        isLimited,
        R.string.liked_movies
    )
    data class Downloaded(override val isLimited: Boolean = false) : CategoryType(
        isLimited,
        R.string.downloaded_movies
    )

}