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

    override suspend fun getFeaturedList(): LoadingStatus {
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

    override suspend fun getMovieById(id: Int): MovieItem? {
        val request = apiService.getMovieById(id)
        return if (request.isSuccessful) {
            request.body()?.toItem()
        } else {
            null
        }
    }
}