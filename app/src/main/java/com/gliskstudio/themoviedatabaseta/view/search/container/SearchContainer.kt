package com.gliskstudio.themoviedatabaseta.view.search.container

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.AppNavigation
import com.gliskstudio.themoviedatabaseta.view.category.CategoryScreen
import com.gliskstudio.themoviedatabaseta.view.details.DetailsScreen
import com.gliskstudio.themoviedatabaseta.view.main.MainScreen
import com.gliskstudio.themoviedatabaseta.view.search.SearchScreen

@Composable
fun SearchContainer(
    controller: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {

    val backStackEntry by controller.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination?.route
    val isSearchBarVisible: Boolean
    val isPaddingRequired: Boolean

    when (currentDestination) {
        MainScreen.route -> {
            isSearchBarVisible = true
            isPaddingRequired = true
        }
        CategoryScreen.route -> {
            isSearchBarVisible = true
            isPaddingRequired = true
        }
        SearchScreen.route -> {
            isSearchBarVisible = true
            isPaddingRequired = true
        }
        DetailsScreen.route -> {
            isSearchBarVisible = false
            isPaddingRequired = false
        }
        else -> {
            isSearchBarVisible = false
            isPaddingRequired = true
        }

        // TODO Fix item is instantly closing while search cursor visible
    }

    val paddingTop = paddingValues.calculateTopPadding() + 10.dp
    val animatedPadding by animateDpAsState(
        targetValue = if (isPaddingRequired) paddingTop else 0.dp,
        tween(300)
    )
    val layoutDirection = LocalLayoutDirection.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(
                paddingValues.calculateStartPadding(layoutDirection),
                animatedPadding,
                paddingValues.calculateEndPadding(layoutDirection),
                paddingValues.calculateBottomPadding(),
            )
    ) {
        StyledSearchBar(isSearchBarVisible, controller)
        AppNavigation(controller, paddingTop)
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    val controller = rememberNavController()
    Scaffold {
        SearchContainer(controller, it)
    }
}