package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetFeaturedFlow @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke() : StateFlow<LoadingStatus> {
        return repository.featuresListState
    }
}