package com.gliskstudio.themoviedatabaseta.view.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gliskstudio.themoviedatabaseta.view.main.MainScreen

@Composable
fun SearchContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column (modifier = modifier) {
        // TODO Search bar + content
        content()
    }
}

@Preview
@Composable
private fun Preview() {
    SearchContainer {
        MainScreen()
    }
}