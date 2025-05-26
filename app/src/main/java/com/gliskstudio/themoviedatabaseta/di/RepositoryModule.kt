package com.gliskstudio.themoviedatabaseta.di

import com.gliskstudio.themoviedatabaseta.data.repository.SearchRepositoryImpl
import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}