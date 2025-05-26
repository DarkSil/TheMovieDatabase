package com.gliskstudio.themoviedatabaseta.domain.repository

import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus

interface SearchRepository {
    suspend fun getFeaturedList() : LoadingStatus
}