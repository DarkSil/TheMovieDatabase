package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.repository.LocalSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedListUseCase @Inject constructor(
    private val repository: LocalSearchRepository
) {
    operator fun invoke(): Flow<List<Int>> {
        return repository.getLikedList()
    }
}