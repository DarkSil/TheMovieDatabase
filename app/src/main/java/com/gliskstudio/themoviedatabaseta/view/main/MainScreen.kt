package com.gliskstudio.themoviedatabaseta.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.model.CategoryType
import com.gliskstudio.themoviedatabaseta.model.LoadStatus
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.category.CategoryScreen
import com.gliskstudio.themoviedatabaseta.view.category.section.CategorySection
import com.gliskstudio.themoviedatabaseta.view.main.purposeSection.PurposeSection

object MainScreen {
    const val route = "main"
}

@Composable
fun MainScreen (controller: NavHostController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
            .verticalScroll(scrollState)
    ) {
        // TODO Get from preferences
        val isPurposeVisible = rememberSaveable { mutableStateOf(true) }

        // TODO Get from viewmodel
        val status = LoadStatus.Loaded(Utils.mockMovieList(true))

        val onItemClick: (id: Int) -> Unit = { id ->
            // TODO Navigate to item
        }

        val onCategoryClick: (categoryType: CategoryType) -> Unit = { categoryType ->
            controller.navigate(CategoryScreen.prepareRoute(categoryType.categoryId))
        }

        PurposeSection(isPurposeVisible) {
            isPurposeVisible.value = false
        }

        CategorySection(
            categoryType = CategoryType.Featured(true),
            status,
            onCategoryClick = onCategoryClick,
            onItemClick = onItemClick
        )
        CategorySection(
            categoryType = CategoryType.Liked(true),
            status,
            onCategoryClick = onCategoryClick,
            onItemClick = onItemClick
        )
        CategorySection(
            categoryType = CategoryType.Downloaded(true),
            status,
            onCategoryClick = onCategoryClick,
            onItemClick = onItemClick
        )

        Spacer(Modifier.height(16.dp))

    }
}

@Composable
@Preview (apiLevel = 34)
private fun Preview () {
    val controller = rememberNavController()
    Scaffold { padding ->
        MainScreen(controller)
    }
}