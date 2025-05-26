package com.gliskstudio.themoviedatabaseta.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetFeaturesListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class SearchViewModel (
    private val getFeaturesListUseCase: GetFeaturesListUseCase
) : ViewModel() {

    private val _featuresListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress)
    val featuresListState : StateFlow<LoadingStatus> = _featuresListState

    fun loadFeatures() {
        viewModelScope.launch {
            _featuresListState.value = LoadingStatus.InProgress
            _featuresListState.value = getFeaturesListUseCase()
        }
    }

    init {
        loadFeatures()
    }

}