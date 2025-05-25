package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gliskstudio.themoviedatabaseta.model.CategoryType
import com.gliskstudio.themoviedatabaseta.utils.Utils

@Composable
fun CategorySection(
    categoryType: CategoryType,
    onCategoryClick: (categoryType: CategoryType) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
    ) {
        CategoryTitle(
            categoryType,
            onCategoryClick
        )
        // TODO Get the list
        CategoryList(Utils.mockMovieList(), categoryType.isLimited, onItemClick)
    }
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    Scaffold { padding ->
        CategorySection(CategoryType.Featured(true), {}, {})
    }
}