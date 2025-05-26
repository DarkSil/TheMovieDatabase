package com.gliskstudio.themoviedatabaseta.data.remote.api

import com.gliskstudio.themoviedatabaseta.data.remote.model.MovieDto
import com.gliskstudio.themoviedatabaseta.data.remote.model.MoviesListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/discover/movie?include_adult=false&include_video=true&language=en-US&sort_by=popularity.desc")
    suspend fun getFeaturesList(@Query("page") page: Int): Response<MoviesListDto>

    @GET("3/movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Response<MovieDto>

    @GET("3/search/movie?include_adult=false&language=en-US")
    suspend fun getSearchedList(@Query("page") page: Int, @Query("query") query: String): Response<MoviesListDto>

}