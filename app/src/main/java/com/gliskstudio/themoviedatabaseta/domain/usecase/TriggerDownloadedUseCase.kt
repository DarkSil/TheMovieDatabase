package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.repository.LocalSearchRepository
import javax.inject.Inject

class TriggerDownloadedUseCase @Inject constructor(
    private val repository: LocalSearchRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.triggerDownloadedItem(id)
    }
}