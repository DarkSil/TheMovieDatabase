package com.gliskstudio.themoviedatabaseta.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gliskstudio.themoviedatabaseta.di.DownloadedDataStore
import com.gliskstudio.themoviedatabaseta.di.LikedDataStore
import com.gliskstudio.themoviedatabaseta.domain.repository.LocalSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSearchRepositoryImpl @Inject constructor(
    @LikedDataStore private val likedDataStore: DataStore<Preferences>,
    @DownloadedDataStore private val downloadedDataStore: DataStore<Preferences>
) : LocalSearchRepository{

    private val key = stringPreferencesKey("ids_key")

    // TODO Reload list only if not a check requested (!isLiked)
    override fun getLikedList(): Flow<List<Int>> {
        return likedDataStore.data.map {
            it[key]?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
        }
    }

    override fun getDownloadedList(): Flow<List<Int>> {
        return downloadedDataStore.data.map {
            it[key]?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
        }
    }

    override suspend fun triggerLikedItem(id: Int) {
        likedDataStore.edit {
            val current = it[key]
                ?.split(",")
                ?.mapNotNull { it.toIntOrNull() }
                ?.toMutableList() ?: mutableListOf()

            if (!current.contains(id))
                current.add(id)
            else
                current.removeAt(current.indexOf(id))

            it[key] = current.joinToString(",")
        }
    }

    override suspend fun triggerDownloadedItem(id: Int) {
        downloadedDataStore.edit {
            val current = it[key]
                ?.split(",")
                ?.mapNotNull { it.toIntOrNull() }
                ?.toMutableList() ?: mutableListOf()

            if (!current.contains(id))
                current.add(id)
            else
                current.removeAt(current.indexOf(id))

            it[key] = current.joinToString(",")
        }
    }

    override fun isLiked(id: Int): Flow<Boolean> {
        return getLikedList()
            .map { list -> id in list }
            .distinctUntilChanged()
    }

    override fun isDownloaded(id: Int): Flow<Boolean> {
        return getDownloadedList()
            .map { list -> id in list }
            .distinctUntilChanged()
    }

}