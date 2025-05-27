package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gliskstudio.themoviedatabaseta.domain.model.CategoryType
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.category.section.item.CategoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CategoryList(
    statusFlow: StateFlow<LoadingStatus>,
    categoryType: CategoryType,
    modifier: Modifier = Modifier,
    onItemClick: (id: Int) -> Unit
) {
    val lastLoadedList = remember { mutableStateListOf<MovieItem>() }
    val status by statusFlow.collectAsState()

    // TODO Move this implementation to viewmodel
    val list = when (status) {
        is LoadingStatus.Loaded -> {
            lastLoadedList.clear()
            lastLoadedList.addAll((status as LoadingStatus.Loaded).list)
            lastLoadedList
        }
        else -> lastLoadedList
    }

    if (categoryType.isLimited) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            list.take(3).forEach { item ->
                CategoryItem(item, onItemClick)
            }
        }
    } else {

        // I am forced to separate this due to Composes unavailability to contain few scrollable containers at once

        val activity = LocalActivity.current as ComponentActivity
        val sharedViewModel = hiltViewModel<SharedViewModel>(activity)

        val state = rememberLazyListState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
            state = state
        ) {
            items(list, key = { it.id }) {
                CategoryItem(it, onItemClick)
            }

            // TODO Add circular progress as last item
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            if (status is LoadingStatus.Loaded) {
                                when (categoryType) {
                                    is CategoryType.Downloaded -> {}
                                    is CategoryType.Featured -> sharedViewModel.loadFeatures()
                                    is CategoryType.Liked -> {}
                                    is CategoryType.Searched -> sharedViewModel.loadSearched()
                                }
                            }
                        }
                )
            }
        }
    }
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    Scaffold { padding ->
        CategoryList(
            MutableStateFlow(LoadingStatus.Loaded(Utils.mockMovieList(true))),
            CategoryType.Featured(true)
        ) { }
    }
}