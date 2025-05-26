package com.gliskstudio.themoviedatabaseta.data.repository

import com.gliskstudio.themoviedatabaseta.data.remote.api.ApiService
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {

    private var nextPage = 1
    private var lastRequestedPage = 0

    private val localFeaturedList: ArrayList<MovieItem> = arrayListOf()

    override suspend fun getFeaturedList(firstOnly: Boolean): LoadingStatus {
        if (firstOnly && lastRequestedPage > 0) {
            return LoadingStatus.Loaded(localFeaturedList)
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
                localFeaturedList.addAll(list)
                return LoadingStatus.Loaded(localFeaturedList)
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

    override suspend fun getSearchedList(query: String): LoadingStatus {
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

                nextSearchedPage++
                localSearchedList.addAll(list)
                return LoadingStatus.Loaded(localSearchedList)
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