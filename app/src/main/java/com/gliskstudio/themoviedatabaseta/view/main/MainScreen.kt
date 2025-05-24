package com.gliskstudio.themoviedatabaseta.view.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gliskstudio.themoviedatabaseta.view.main.purposeSection.PurposeSection

object MainScreen {
    const val route = "main"
}

@Composable
fun MainScreen () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {
        var isPurposeVisible by rememberSaveable { mutableStateOf(true) }

        AnimatedVisibility(
            visible = isPurposeVisible,
            enter = expandVertically(
                tween(500)
            ),
            exit = shrinkVertically(
                tween(500)
            )
        ) {
            PurposeSection {
                isPurposeVisible = false
            }
        }
    }
}

@Composable
@Preview (apiLevel = 34)
private fun Preview () {
    Scaffold { padding ->
        MainScreen()
    }
}