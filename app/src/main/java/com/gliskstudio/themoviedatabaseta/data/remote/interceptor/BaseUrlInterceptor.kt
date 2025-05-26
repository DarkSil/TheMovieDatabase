package com.gliskstudio.themoviedatabaseta.data.remote.interceptor

import coil3.intercept.Interceptor
import coil3.request.ImageResult

class BaseUrlInterceptor(private val baseUrl: String) : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request

        val path = request.data as? String
        if (path != null && !path.startsWith("http")) {
            val newUrl = baseUrl + path
            val newRequest = request.newBuilder().data(newUrl).build()
            return chain.withRequest(newRequest).proceed()
        }

        return chain.proceed()
    }
}