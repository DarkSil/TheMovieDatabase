package com.gliskstudio.themoviedatabaseta.view.main.purposeSection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PurposeSection(
    isVisible: MutableState<Boolean>,
    click : () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible.value,
        enter = expandVertically(
            tween(500)
        ),
        exit = shrinkVertically(
            tween(500)
        )
    ) {
        Column {
            PurposeHeader(click)
            PurposeText()
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    val isVisible = remember { mutableStateOf(true) }
    PurposeSection(isVisible) {}
}