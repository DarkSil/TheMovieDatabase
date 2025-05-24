package com.gliskstudio.themoviedatabaseta.view.main.purposeSection

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PurposeSection(click : () -> Unit) {
    Column {
        PurposeHeader(click)
        PurposeText()
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    PurposeSection {}
}