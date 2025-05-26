package com.gliskstudio.themoviedatabaseta.di

import android.content.Context
import coil3.ImageLoader
import com.gliskstudio.themoviedatabaseta.data.remote.api.ApiService
import com.gliskstudio.themoviedatabaseta.data.remote.interceptor.AuthInterceptor
import com.gliskstudio.themoviedatabaseta.data.remote.interceptor.BaseUrlInterceptor
import com.gliskstudio.themoviedatabaseta.data.remote.interceptor.ErrorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseImageUrl

    @BaseUrl
    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org"

    @BaseImageUrl
    @Provides
    fun provideBaseImageUrl() = "https://image.tmdb.org/t/p/w500"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        errorInterceptor: ErrorInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(errorInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, @BaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context, @BaseImageUrl baseImageUrl: String): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(BaseUrlInterceptor(baseImageUrl))
            }
            .build()
    }
}
