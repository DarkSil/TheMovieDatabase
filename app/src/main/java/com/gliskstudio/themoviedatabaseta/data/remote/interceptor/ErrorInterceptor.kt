package com.gliskstudio.themoviedatabaseta.data.remote.interceptor

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            Toast.makeText(context, "HTTP Error appeared - ${response.code}", Toast.LENGTH_SHORT).show()
        }

        return response
    }
}