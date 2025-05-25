package com.gliskstudio.themoviedatabaseta.view.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.model.CategoryType
import com.gliskstudio.themoviedatabaseta.model.LoadStatus
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.category.section.CategorySection

object CategoryScreen {
    const val categoryArgument = "categoryId"
    const val route = "category/{$categoryArgument}"

    fun prepareRoute(categoryId: Int): String {
        return "category/$categoryId"
    }
}

@Composable
fun CategoryScreen(
    categoryType: CategoryType,
    controller: NavHostController
) {
    val onItemClick : (id: Int) -> Unit = {
        // TODO Item Click
    }
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {
        CategorySection(
            categoryType = categoryType,
            // TODO Load list
            status = LoadStatus.Loaded(Utils.mockMovieList()),
            onCategoryClick = {},
            onItemClick = onItemClick
        )
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    val controller = rememberNavController()
    Scaffold { paddingValues ->
        CategoryScreen(CategoryType.Featured(), controller)
    }
}