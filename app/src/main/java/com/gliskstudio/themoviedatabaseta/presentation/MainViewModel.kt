package com.gliskstudio.themoviedatabaseta.presentation

import com.gliskstudio.themoviedatabaseta.domain.usecase.GetFeaturesListUseCase
import com.gliskstudio.themoviedatabaseta.presentation.base.SearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getFeaturesListUseCase: GetFeaturesListUseCase
) : SearchViewModel(getFeaturesListUseCase) {

    fun loadLiked() {
        // TODO Load Liked
    }

    fun loadDownloaded() {
        // TODO Load downloaded
    }

}