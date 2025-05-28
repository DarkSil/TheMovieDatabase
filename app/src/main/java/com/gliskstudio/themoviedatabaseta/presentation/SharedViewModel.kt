package com.gliskstudio.themoviedatabaseta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import com.gliskstudio.themoviedatabaseta.domain.model.CategoryType
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetFeaturedFlow
import com.gliskstudio.themoviedatabaseta.domain.usecase.GetSearchedFlow
import com.gliskstudio.themoviedatabaseta.domain.usecase.IsDownloadedUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.IsLikedUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.LoadDownloadedListUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.LoadFeaturesFirstPageUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.LoadFeaturesListUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.LoadLikedListUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.LoadMovieByIdUseCase
import com.gliskstudio.themoviedatabaseta.domain.usecase.LoadSearchedListUseCase
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    getFeaturedFlow: GetFeaturedFlow,
    getSearchedFlow: GetSearchedFlow,
    private val loadFeaturesFirstPageUseCase: LoadFeaturesFirstPageUseCase,
    private val loadFeaturesListUseCase: LoadFeaturesListUseCase,
    private val loadSearchedListUseCase: LoadSearchedListUseCase,
    private val loadMovieByIdUseCase: LoadMovieByIdUseCase,

    private val loadLikedListUseCase: LoadLikedListUseCase,
    private val loadDownloadedListUseCase: LoadDownloadedListUseCase,
    private val triggerLikedUseCase: TriggerLikedUseCase,
    private val triggerDownloadedUseCase: TriggerDownloadedUseCase,
    private val isLikedUseCase: IsLikedUseCase,
    private val isDownloadedUseCase: IsDownloadedUseCase,

    val imageLoader: ImageLoader
) : ViewModel() {
    // TODO Separate this viewModel and create local ones using it's own usecases

    private val _likedListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress())
    val likedListState : StateFlow<LoadingStatus> = _likedListState

    private val _downloadedListState = MutableStateFlow<LoadingStatus>(LoadingStatus.InProgress())
    val downloadedListState : StateFlow<LoadingStatus> = _downloadedListState

    private val featuresListState : StateFlow<LoadingStatus> = getFeaturedFlow()
    private val searchedListState : StateFlow<LoadingStatus> = getSearchedFlow()

    val queryTextState = MutableStateFlow("")

    // TODO Move caching to new repository and add to cached items both "searched" and "featured" lists (Or better only the ones being liked or downloaded)
    private val cachedItems = mutableMapOf<Int, MovieItem>()

    init {
        viewModelScope.launch {
            queryTextState.debounce(500)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest {
                    withContext(Dispatchers.IO) {
                        loadSearched()
                    }
                }
        }
    }

    fun loadFeatures() {
        viewModelScope.launch(Dispatchers.IO) {
            loadFeaturesListUseCase()
        }
    }

    fun loadFeaturesFirstPage() {
        viewModelScope.launch(Dispatchers.IO) {
            loadFeaturesFirstPageUseCase()
        }
    }

    suspend fun loadById(id: Int): MovieItem? {
        return loadMovieByIdUseCase(id)
    }

    fun loadLiked() {
        // TODO Move to repository
        val value = _likedListState.value
        if (value is LoadingStatus.Loaded) {
            return
        }
        viewModelScope.launch {
            _likedListState.value = LoadingStatus.InProgress()
            loadLikedListUseCase().collectLatest {
                withContext(Dispatchers.IO) {
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
    }

    fun loadDownloaded() {
        val value = _downloadedListState.value
        if (value is LoadingStatus.Loaded) {
            return
        }
        viewModelScope.launch {
            _downloadedListState.value = LoadingStatus.InProgress()
            loadDownloadedListUseCase().collectLatest {
                withContext(Dispatchers.IO) {
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
    }

    fun loadByCategory(categoryType: CategoryType) {
        when (categoryType) {
            is CategoryType.Featured -> {
                loadFeaturesFirstPage()
            }
            is CategoryType.Searched -> {
                return
            }
            is CategoryType.Downloaded -> {
                loadDownloaded()
            }
            is CategoryType.Liked -> {
                loadLiked()
            }
        }
    }

    fun getFlowByCategory(categoryType: CategoryType): StateFlow<LoadingStatus> {
        return when (categoryType) {
            is CategoryType.Featured -> {
                featuresListState
            }
            is CategoryType.Searched -> {
                searchedListState
            }
            is CategoryType.Downloaded -> {
                downloadedListState
            }
            is CategoryType.Liked -> {
                likedListState
            }
        }
    }

    fun loadSearched() {
        val query = queryTextState.value
        viewModelScope.launch(Dispatchers.IO) {
            loadSearchedListUseCase(query)
        }
    }

    fun triggerLiked(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            triggerLikedUseCase(id)
        }
    }

    fun triggerDownloaded(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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