package com.gliskstudio.themoviedatabaseta.view.main

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.domain.model.CategoryType
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel
import com.gliskstudio.themoviedatabaseta.view.category.CategoryScreen
import com.gliskstudio.themoviedatabaseta.view.category.section.CategorySection
import com.gliskstudio.themoviedatabaseta.view.details.DetailsScreen
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
        val activity = LocalActivity.current as ComponentActivity
        val sharedViewModel = hiltViewModel<SharedViewModel>(activity)

        val likedStatus by sharedViewModel.likedListState.collectAsState()
        val downloadedStatus by sharedViewModel.downloadedListState.collectAsState()

        LaunchedEffect(true) {
            sharedViewModel.loadFeaturesFirstPage()
            sharedViewModel.loadLiked()
            sharedViewModel.loadDownloaded()
        }

        val isPurposeVisible = rememberSaveable { mutableStateOf(true) }

        val onItemClick: (id: Int) -> Unit = { id ->
            controller.navigate(DetailsScreen.prepareRoute(id))
        }

        val onCategoryClick: (categoryType: CategoryType) -> Unit = { categoryType ->
            controller.navigate(CategoryScreen.prepareRoute(categoryType.categoryId))
        }

        PurposeSection(isPurposeVisible) {
            isPurposeVisible.value = false
        }

        CategorySection(
            categoryType = CategoryType.Featured(true),
            onCategoryClick = onCategoryClick,
            onItemClick = onItemClick
        )
        AnimatedVisibility(
            visible = (likedStatus is LoadingStatus.Loaded
                    && (likedStatus as LoadingStatus.Loaded).list.isNotEmpty())
                || likedStatus is LoadingStatus.InProgress,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            CategorySection(
                categoryType = CategoryType.Liked(true),
                onCategoryClick = onCategoryClick,
                onItemClick = onItemClick
            )
        }
        AnimatedVisibility(
            visible = (downloadedStatus is LoadingStatus.Loaded
                    && (downloadedStatus as LoadingStatus.Loaded).list.isNotEmpty())
                    || downloadedStatus is LoadingStatus.InProgress,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            CategorySection(
                categoryType = CategoryType.Downloaded(true),
                onCategoryClick = onCategoryClick,
                onItemClick = onItemClick
            )
        }

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