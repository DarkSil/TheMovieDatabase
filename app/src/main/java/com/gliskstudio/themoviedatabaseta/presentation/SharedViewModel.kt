package com.gliskstudio.themoviedatabaseta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetDownloadedListUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetFeaturesFirstPageUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetFeaturesListUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetLikedListUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetMovieByIdUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetSearchedListUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.IsDownloadedUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.IsLikedUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.TriggerDownloadedUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.TriggerLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getFeaturesFirstPageUseCase: GetFeaturesFirstPageUseCase,
    private val getFeaturesListUseCase: GetFeaturesListUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getLikedListUseCase: GetLikedListUseCase,
    private val getDownloadedListUseCase: GetDownloadedListUseCase,
    private val triggerLikedUseCase: TriggerLikedUseCase,
    private val triggerDownloadedUseCase: TriggerDownloadedUseCase,
    private val isLikedUseCase: IsLikedUseCase,
    private val isDownloadedUseCase: IsDownloadedUseCase,
    private val getSearchedListUseCase: GetSearchedListUseCase,
    val imageLoader: ImageLoader
) : ViewModel() {

    private val _featuresListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress)
    val featuresListState : StateFlow<LoadingStatus> = _featuresListState

    private val _likedListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress)
    val likedListState : StateFlow<LoadingStatus> = _likedListState

    private val _downloadedListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress)
    val downloadedListState : StateFlow<LoadingStatus> = _downloadedListState

    private val _searchedListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress)
    val searchedListState : StateFlow<LoadingStatus> = _searchedListState

    val queryTextState = MutableStateFlow("")

    private val cachedItems = mutableMapOf<Int, MovieItem>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            queryTextState.debounce(500)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest {
                    loadSearched(queryTextState.value)
                }
        }
    }

    fun loadFeatures() {
        viewModelScope.launch(Dispatchers.IO) {
            _featuresListState.value = LoadingStatus.InProgress
            _featuresListState.value = getFeaturesListUseCase()
        }
    }

    fun loadFeaturesFirstPage() {
        viewModelScope.launch(Dispatchers.IO) {
            _featuresListState.value = LoadingStatus.InProgress
            _featuresListState.value = getFeaturesFirstPageUseCase()
        }
    }

    suspend fun loadById(id: Int): MovieItem? {
        return getMovieByIdUseCase(id)
    }

    fun loadLiked() {
        val value = _likedListState.value
        if (value is LoadingStatus.Loaded) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _likedListState.value = LoadingStatus.InProgress
            getLikedListUseCase().collectLatest {
                val results = it.map { id ->
                    async(Dispatchers.IO) {
                        cachedItems[id] ?: loadById(id)?.also { item ->
                            cachedItems[id] = item
                        }
                    }
                }
                _likedListState.value = LoadingStatus.Loaded(results.mapNotNull { it.await() })
            }
        }
    }

    fun loadDownloaded() {
        val value = _downloadedListState.value
        if (value is LoadingStatus.Loaded) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _downloadedListState.value = LoadingStatus.InProgress
            getDownloadedListUseCase().collectLatest {
                val results = it.map { id ->
                    async(Dispatchers.IO) {
                        cachedItems[id] ?: loadById(id)?.also { item ->
                            cachedItems[id] = item
                        }
                    }
                }
                _downloadedListState.value =
                    LoadingStatus.Loaded(results.mapNotNull { it.await() })
            }
        }
    }

    fun loadSearched(query: String = queryTextState.value) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedListState.value = LoadingStatus.InProgress
            _searchedListState.value = getSearchedListUseCase(query)
        }
    }

    fun triggerLiked(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _likedListState.value = LoadingStatus.InProgress
            triggerLikedUseCase(id)
        }
    }

    fun triggerDownloaded(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _downloadedListState.value = LoadingStatus.InProgress
            triggerDownloadedUseCase(id)
        }
    }

    fun isLiked(id: Int) : Flow<Boolean> {
        return isLikedUseCase(id)
    }

    fun isDownloaded(id: Int) : Flow<Boolean> {
        return isDownloadedUseCase(id)
    }

    fun onValueChange(it: String) {
        queryTextState.value = it
    }

}