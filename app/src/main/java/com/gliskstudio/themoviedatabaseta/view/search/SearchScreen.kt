package com.gliskstudio.themoviedatabaseta.view.search

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

object SearchScreen {
    const val route = "search"
}

@Composable
fun SearchScreen(controller: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {

        val activity = LocalActivity.current as ComponentActivity
        val sharedViewModel = hiltViewModel<SharedViewModel>(activity)

        LaunchedEffect(true) {
            sharedViewModel.loadSearched()
        }

        val categoryType = CategoryType.Searched()
        val status by sharedViewModel.searchedListState.collectAsState()

        CategorySection(
            categoryType = categoryType,
            status = status,
            onCategoryClick = {},
            onItemClick = { id ->
                controller.navigate(DetailsScreen.prepareRoute(id))
            }
        )
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    Scaffold { padding ->
        val controller = rememberNavController()
        SearchScreen(controller)
    }
}