package com.gliskstudio.themoviedatabaseta.domain.repository

import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem

interface SearchRepository {
    suspend fun getFeaturedList() : LoadingStatus
    suspend fun getMovieById(id: Int) : MovieItem?
}