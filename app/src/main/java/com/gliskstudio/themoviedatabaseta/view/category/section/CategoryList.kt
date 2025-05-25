package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gliskstudio.themoviedatabaseta.model.MovieItem
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.category.section.item.CategoryItem

@Composable
fun CategoryList(
    list: ArrayList<MovieItem>,
    isLimited: Boolean,
    onItemClick: (id: Int) -> Unit
) {
    LazyColumn (
        modifier = if (isLimited) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.fillMaxSize()
        }
    ) {
        items(list) {
            CategoryItem(it, onItemClick)
        }
    }
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    Scaffold { padding ->
        CategoryList(
            Utils.mockMovieList(),
            true
        ) { }
    }
}