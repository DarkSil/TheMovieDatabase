package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gliskstudio.themoviedatabaseta.domain.model.CategoryType
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel

@Composable
fun CategorySection(
    categoryType: CategoryType,
    onCategoryClick: (categoryType: CategoryType) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CategoryTitle(
            categoryType,
            onCategoryClick
        )

        val activity = LocalActivity.current as ComponentActivity
        val sharedViewModel = hiltViewModel<SharedViewModel>(activity)

        LaunchedEffect(true) {
            sharedViewModel.loadByCategory(categoryType)
        }

        val statusFlow = sharedViewModel.getFlowByCategory(categoryType)
        val status by statusFlow.collectAsState()

        val isPageLoading: Boolean = if (status is LoadingStatus.InProgress) {
            (status as LoadingStatus.InProgress).isPageLoading
        } else {
            false
        }

        CategoryList(
            statusFlow,
            categoryType,
            onItemClick
        )

        /*AnimatedVisibility(
            visible = list.isNotEmpty() || isPageLoading,
            enter = fadeIn(tween(200, 100)),
            exit = fadeOut(tween(200))
        ) {

        }*/

        AnimatedVisibility(
            visible = status is LoadingStatus.InProgress && !isPageLoading,
            enter = fadeIn(tween(200, 200)),
            exit = fadeOut(tween(200))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 24.dp)
            ) {
                // TODO Custom
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        CategoryListText(statusFlow)
    }
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    Scaffold { padding ->
        CategorySection(
            categoryType = CategoryType.Featured(true),
            onCategoryClick = {},
            onItemClick = {}
        )
    }
}