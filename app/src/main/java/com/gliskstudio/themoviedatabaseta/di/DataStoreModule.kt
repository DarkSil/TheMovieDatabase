package com.gliskstudio.themoviedatabaseta.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LikedDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DownloadedDataStore

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.likedStore: DataStore<Preferences> by preferencesDataStore(name = "liked_prefs")
    private val Context.downloadedStore: DataStore<Preferences> by preferencesDataStore(name = "downloaded_prefs")

    @LikedDataStore
    @Provides
    @Singleton
    fun provideLikedDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.likedStore
    }

    @DownloadedDataStore
    @Provides
    @Singleton
    fun provideDownloadedDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.downloadedStore
    }
}