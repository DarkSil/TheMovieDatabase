package com.gliskstudio.themoviedatabaseta

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gliskstudio.themoviedatabaseta.model.CategoryType
import com.gliskstudio.themoviedatabaseta.view.category.CategoryScreen
import com.gliskstudio.themoviedatabaseta.view.main.MainScreen
import com.gliskstudio.themoviedatabaseta.view.search.SearchScreen

@Composable
fun AppNavigation(controller: NavHostController) {
    NavHost(
        controller,
        startDestination = MainScreen.route
    ) {
        composable(MainScreen.route) {
            MainScreen(controller)
        }
        composable(
            route = CategoryScreen.route,
            arguments = listOf(
                navArgument(CategoryScreen.categoryArgument) {
                    type = NavType.IntType
                }
            )
        ) {
            // It must always have a value but in case it won't it should just crash the app
            val id = it.arguments?.getInt(CategoryScreen.categoryArgument) ?: -1
            val category = CategoryType.fromCategoryId(id)
            CategoryScreen(category, controller)
        }
        composable(SearchScreen.route) {
            SearchScreen(controller)
        }
    }
}