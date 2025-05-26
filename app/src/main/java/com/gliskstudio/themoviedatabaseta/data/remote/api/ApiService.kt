package com.gliskstudio.themoviedatabaseta.data.remote.api

import com.gliskstudio.themoviedatabaseta.data.remote.model.MoviesListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("3/discover/movie?include_adult=false&include_video=true&language=en-US&sort_by=popularity.desc")
    suspend fun getFeaturesList(@Query("page") page: Int): Response<MoviesListDto>

}