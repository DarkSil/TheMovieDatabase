package com.gliskstudio.themoviedatabaseta.domain.repository

import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import kotlinx.coroutines.flow.StateFlow

interface SearchRepository {
    val featuresListState: StateFlow<LoadingStatus>
    val searchedListState: StateFlow<LoadingStatus>

    suspend fun getFeaturedList(firstOnly: Boolean)
    suspend fun getSearchedList(query: String)
    suspend fun getMovieById(id: Int) : MovieItem?
}