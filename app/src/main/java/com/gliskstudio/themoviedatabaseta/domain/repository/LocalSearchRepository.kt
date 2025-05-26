package com.gliskstudio.themoviedatabaseta.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalSearchRepository {
    fun getLikedList() : Flow<List<Int>>
    fun getDownloadedList() : Flow<List<Int>>
    suspend fun triggerLikedItem(id: Int)
    suspend fun triggerDownloadedItem(id: Int)
    fun isLiked(id: Int) : Flow<Boolean>
    fun isDownloaded(id: Int) : Flow<Boolean>
}