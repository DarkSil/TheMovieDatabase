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
import com.gliskstudio.themoviedatabaseta.model.CategoryType
import com.gliskstudio.themoviedatabaseta.view.category.section.CategorySection
import com.gliskstudio.themoviedatabaseta.view.main.purposeSection.PurposeSection

object MainScreen {
    const val route = "main"
}

@Composable
fun MainScreen () {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
            .verticalScroll(scrollState)
    ) {
        val isPurposeVisible = rememberSaveable { mutableStateOf(true) }

        val onItemClick: (id: Int) -> Unit = { id ->
            // TODO Navigate to item
        }

        val onCategoryClick: (categoryType: CategoryType) -> Unit = { categoryType ->
            // TODO Navigate to category
        }

        PurposeSection(isPurposeVisible) {
            isPurposeVisible.value = false
        }

        CategorySection(
            CategoryType.Featured(true),
            onCategoryClick,
            onItemClick
        )
        CategorySection(
            CategoryType.Liked(true),
            onCategoryClick,
            onItemClick
        )
        CategorySection(
            CategoryType.Downloaded(true),
            onCategoryClick,
            onItemClick
        )

        Spacer(Modifier.height(16.dp))

    }
}

@Composable
@Preview (apiLevel = 34)
private fun Preview () {
    Scaffold { padding ->
        MainScreen()
    }
}