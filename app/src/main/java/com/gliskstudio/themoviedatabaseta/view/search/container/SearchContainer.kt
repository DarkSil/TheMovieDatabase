package com.gliskstudio.themoviedatabaseta.view.search.container

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.view.category.CategoryScreen
import com.gliskstudio.themoviedatabaseta.view.main.MainScreen
import com.gliskstudio.themoviedatabaseta.view.search.SearchScreen

@Composable
fun SearchContainer(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val backStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination?.route
    val isSearchBarVisible = when (currentDestination) {
        MainScreen.route -> true
        CategoryScreen.route -> true
        SearchScreen.route -> true
        else -> false
    }

    Column (modifier = modifier) {
        StyledSearchBar(isSearchBarVisible, controller)
        content()
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    val controller = rememberNavController()
    SearchContainer(controller) {}
}