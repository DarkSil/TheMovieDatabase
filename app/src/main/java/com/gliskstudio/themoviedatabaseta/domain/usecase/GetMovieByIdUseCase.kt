package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(id: Int): MovieItem? {
        return searchRepository.getMovieById(id)
    }
}