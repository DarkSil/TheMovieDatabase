package com.gliskstudio.themoviedatabaseta.view.category

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.domain.model.CategoryType
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel
import com.gliskstudio.themoviedatabaseta.view.category.section.CategorySection
import com.gliskstudio.themoviedatabaseta.view.details.DetailsScreen

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
    val activity = LocalActivity.current as ComponentActivity
    val sharedViewModel = hiltViewModel<SharedViewModel>(activity)

    val status by when (categoryType) {
        is CategoryType.Featured -> {
            sharedViewModel.loadFeaturesFirstPage()
            sharedViewModel.featuresListState.collectAsState()
        }
        is CategoryType.Downloaded -> {
            sharedViewModel.loadDownloaded()
            sharedViewModel.downloadedListState.collectAsState()
        }
        is CategoryType.Liked -> {
            sharedViewModel.loadLiked()
            sharedViewModel.likedListState.collectAsState()
        }
        is CategoryType.Searched -> {
            // Search will never appear here
            sharedViewModel.loadSearched()
            sharedViewModel.searchedListState.collectAsState()
        }
    }

    val onItemClick : (id: Int) -> Unit = { id ->
        controller.navigate(DetailsScreen.prepareRoute(id))
    }
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {
        CategorySection(
            categoryType = categoryType,
            status = status,
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