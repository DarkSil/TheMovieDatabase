package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gliskstudio.themoviedatabaseta.domain.model.CategoryType
import com.gliskstudio.themoviedatabaseta.domain.model.MovieItem
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.category.section.item.CategoryItem

@Composable
fun CategoryList(
    list: List<MovieItem>,
    categoryType: CategoryType,
    onItemClick: (id: Int) -> Unit
) {
    if (categoryType.isLimited) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val size = if (list.size <= 3) list.size else 3
            for (position in 0..< size) {
                CategoryItem(list[position], onItemClick)
            }
        }
    } else {
        val activity = LocalActivity.current as ComponentActivity
        val sharedViewModel = hiltViewModel<SharedViewModel>(activity)

        val state = rememberLazyListState()

        if (list.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = state
            ) {
                items(list, key = { it.id }) {
                    CategoryItem(it, onItemClick)
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                when (categoryType) {
                                    is CategoryType.Downloaded -> {}
                                    is CategoryType.Featured -> sharedViewModel.loadFeatures()
                                    is CategoryType.Liked -> {}
                                    is CategoryType.Searched -> sharedViewModel.loadSearched()
                                }
                            }
                    )
                }
            }
        }
    }
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    Scaffold { padding ->
        CategoryList(
            Utils.mockMovieList(true),
            CategoryType.Featured(true)
        ) { }
    }
}