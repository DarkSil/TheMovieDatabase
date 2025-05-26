package com.gliskstudio.themoviedatabaseta.view.main.purposeSection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurfaceVariant


@Composable
fun PurposeText() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.application_purpose),
            color = OnSurfaceVariant,
            fontSize = 11.sp
        )
        Text(
            text = stringResource(R.string.purpose_text),
            fontSize = 14.sp
        )
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    PurposeText()
}