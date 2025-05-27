package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import javax.inject.Inject

class LoadFeaturesFirstPageUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke() {
        return repository.getFeaturedList(true)
    }
}