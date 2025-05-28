package com.gliskstudio.themoviedatabaseta.view.category.section

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gliskstudio.themoviedatabaseta.R
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

        val isLoadingShown = status is LoadingStatus.InProgress && !isPageLoading
        val isNotificationShown = status is LoadingStatus.EmptyList || status is LoadingStatus.EmptyQuery

        val alpha by animateFloatAsState(
            targetValue = if (isLoadingShown || isNotificationShown) 0f else 1f,
            animationSpec = tween(500)
        )

        Box {
            CategoryList(
                statusFlow,
                categoryType,
                Modifier.alpha(alpha),
                onItemClick
            )

            androidx.compose.animation.AnimatedVisibility (
                visible = isLoadingShown,
                enter = fadeIn(tween(500)),
                exit = fadeOut(tween(500))
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

            // TODO notify user on empty query
            CategoryNotificationText(
                statusFlow,
                modifier = Modifier.alpha(
                    if (isNotificationShown) 1f - alpha else 0f
                )
            )
        }

        if (status is LoadingStatus.Error) {
            val context = LocalContext.current
            val errorCode = (status as LoadingStatus.Error).errorCode
            val text = stringResource(R.string.oops_message, errorCode)

            LaunchedEffect(status) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
        }
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