package com.gliskstudio.themoviedatabaseta.domain.repository

import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem

interface SearchRepository {
    suspend fun getFeaturedList(firstOnly: Boolean) : LoadingStatus
    suspend fun getSearchedList(query: String) : LoadingStatus
    suspend fun getMovieById(id: Int) : MovieItem?
}