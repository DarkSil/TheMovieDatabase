package com.gliskstudio.themoviedatabaseta.model

import com.gliskstudio.themoviedatabaseta.R

sealed class CategoryType(
    open val isLimited: Boolean,
    val categoryTitleId: Int,
    val categoryId: Int
) {

    data class Featured(override val isLimited: Boolean = false) : CategoryType(
        isLimited,
        R.string.featured_movies,
        0
    )
    data class Liked(override val isLimited: Boolean = false) : CategoryType(
        isLimited,
        R.string.liked_movies,
        1
    )
    data class Downloaded(override val isLimited: Boolean = false) : CategoryType(
        isLimited,
        R.string.downloaded_movies,
        2
    )
    data class Searched(override val isLimited: Boolean = false) : CategoryType(
        isLimited,
        R.string.searched_movies,
        3
    )

    class UnknownCategoryTypeException(id: Int) : IllegalArgumentException("Unknown category id: $id")

    companion object {
        fun fromCategoryId(id: Int, isLimited: Boolean = false) : CategoryType {
            return when (id) {
                0 -> Featured(isLimited)
                1 -> Liked(isLimited)
                2 -> Downloaded(isLimited)
                3 -> Searched(isLimited)
                else -> throw UnknownCategoryTypeException(id)
            }
        }
    }

}