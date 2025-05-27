package com.gliskstudio.themoviedatabaseta.domain.usecase

import com.gliskstudio.themoviedatabaseta.domain.repository.SearchRepository
import javax.inject.Inject

class LoadSearchedListUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String) {
        return repository.getSearchedList(query)
    }
}