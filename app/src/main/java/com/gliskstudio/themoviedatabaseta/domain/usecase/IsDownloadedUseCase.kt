package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.repository.LocalSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsDownloadedUseCase @Inject constructor(
    private val repository: LocalSearchRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> {
        return repository.isDownloaded(id)
    }
}