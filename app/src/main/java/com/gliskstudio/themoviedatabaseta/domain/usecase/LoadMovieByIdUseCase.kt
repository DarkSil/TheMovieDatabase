package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import javax.inject.Inject

class LoadMovieByIdUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    // TODO optimize loading to load a batch and not one by one + Implement loading for limited list (3 items only)
    suspend operator fun invoke(id: Int): MovieItem? {
        return searchRepository.getMovieById(id)
    }
}