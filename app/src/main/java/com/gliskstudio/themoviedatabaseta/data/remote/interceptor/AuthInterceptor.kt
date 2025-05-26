package com.gliskstudio.themoviedatabaseta.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    // Hardcoded. I do not have a server to pass it
    private val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOGU3NjM4OWQzMjdmYjgwOWI4M2M2NTA5ZDNiNzNkNSIsIm5iZiI6MTc0ODIyMzk4OC4xMjQsInN1YiI6IjY4MzNjN2Y0ZDhjNWE4MmRiNTAzNzM1YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.g4AC_Cx9iy-QK8P0chTmy0xaGHk7apeYvuDwvcWSnaQ"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer $accessToken")

        return chain.proceed(requestBuilder.build())
    }
}