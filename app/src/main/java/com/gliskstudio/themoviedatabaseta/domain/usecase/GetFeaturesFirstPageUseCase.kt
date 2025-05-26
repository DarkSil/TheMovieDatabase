package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import javax.inject.Inject

class GetFeaturesFirstPageUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(): LoadingStatus {
        return repository.getFeaturedList(true)
    }
}