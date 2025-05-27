package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import javax.inject.Inject

class LoadFeaturesFirstPageUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    // TODO Pass a separate list with 3 items only to optimize the display of list
    suspend operator fun invoke() {
        return repository.getFeaturedList(true)
    }
}