package com.gliskstudio.themoviedatabaseta.data.repository

import com.gliskstudio.themoviedatabaseta.data.remote.api.ApiService
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    private var nextPage = 1
    private var lastRequestedPage = 0

    private val localFeaturedList: ArrayList<MovieItem> = arrayListOf()

    private val _featuresListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress())
    override val featuresListState : StateFlow<LoadingStatus> = _featuresListState

    override suspend fun getFeaturedList(firstOnly: Boolean) {
        _featuresListState.value = loadFeaturedList(firstOnly)
    }

    private suspend fun loadFeaturedList(firstOnly: Boolean): LoadingStatus {
        // TODO Check if it's really required
        if (firstOnly && lastRequestedPage > 0) {
            return LoadingStatus.Loaded(localFeaturedList.toList())
        }

        if (nextPage > lastRequestedPage) {
            val request = apiService.getFeaturesList(++lastRequestedPage)
            if (request.isSuccessful) {

                val list = request.body()?.results?.map {
                    it.toItem()
                }

                if (list.isNullOrEmpty()) {
                    lastRequestedPage--
                    return LoadingStatus.Error(-1)
                }

                nextPage++

                val noDuplicatesList = list.filterNot { item ->
                    localFeaturedList.any { it.id == item.id }
                }
                localFeaturedList.addAll(noDuplicatesList)
                return LoadingStatus.Loaded(localFeaturedList.toList())
            } else {
                return when (request.code()) {
                    400 -> LoadingStatus.LimitExceeded
                    else -> LoadingStatus.Error(request.code())
                }
            }
        }
        return LoadingStatus.PageOverload
    }

    private var nextSearchedPage = 1
    private var lastSearchedRequestedPage = 0
    private var lastQuery : String? = null

    private val localSearchedList: ArrayList<MovieItem> = arrayListOf()

    private val _searchedListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress())
    override val searchedListState : StateFlow<LoadingStatus> = _searchedListState

    override suspend fun getSearchedList(query: String) {
        _searchedListState.value = loadSearchedList(query)
    }

    // TODO Develop the way to cancel last request in case new is coming
    private suspend fun loadSearchedList(query: String): LoadingStatus {
        if (query != lastQuery) {
            nextSearchedPage = 1
            lastSearchedRequestedPage = 0
            localSearchedList.clear()
        }

        if (nextSearchedPage > lastSearchedRequestedPage) {
            val request = apiService.getSearchedList(++lastSearchedRequestedPage, query)
            lastQuery = query
            if (request.isSuccessful) {

                val list = request.body()?.results?.map {
                    it.toItem()
                }

                if (list == null) {
                    lastSearchedRequestedPage--
                    return LoadingStatus.Error(-1)
                }

                if (list.isEmpty()) {
                    return LoadingStatus.LimitExceeded
                }

                nextSearchedPage++
                val noDuplicatesList = list.filterNot { item ->
                    localSearchedList.any { it.id == item.id }
                }
                localSearchedList.addAll(noDuplicatesList)
                return LoadingStatus.Loaded(localSearchedList.toList())
            } else {
                return when (request.code()) {
                    400 -> LoadingStatus.LimitExceeded
                    else -> LoadingStatus.Error(request.code())
                }
            }
        }
        return LoadingStatus.PageOverload
    }

    override suspend fun getMovieById(id: Int): MovieItem? {
        val request = apiService.getMovieById(id)
        return if (request.isSuccessful) {
            request.body()?.toItem()
        } else {
            null
        }
    }
}