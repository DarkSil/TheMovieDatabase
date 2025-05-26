package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.repository.LocalSearchRepository
import javax.inject.Inject

class TriggerLikedUseCase @Inject constructor(
    private val repository: LocalSearchRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.triggerLikedItem(id)
    }
}