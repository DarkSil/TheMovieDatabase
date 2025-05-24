package com.gliskstudio.themoviedatabaseta

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gliskstudio.themoviedatabaseta.view.main.MainScreen
import com.gliskstudio.themoviedatabaseta.view.search.SearchContainer

@Composable
fun AppNavigation(controller: NavHostController) {
    NavHost(
        controller,
        startDestination = MainScreen.route
    ) {
        composable(MainScreen.route) {
            SearchContainer {
                MainScreen()
            }
        }
    }
}