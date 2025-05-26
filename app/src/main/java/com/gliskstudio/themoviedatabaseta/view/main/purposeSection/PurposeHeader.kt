package com.gliskstudio.themoviedatabaseta.view.main.purposeSection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurfaceVariant

@Composable
fun PurposeHeader(click : () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.app_logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(136.dp)
                .clip(RoundedCornerShape(28.dp))
        )
        Column (

        ) {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.tech_assignment),
                fontSize = 16.sp,
                color = OnSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 4.dp, 0.dp, 0.dp)
            )

            Button(
                onClick = click,
                modifier = Modifier
                    .padding(0.dp, 24.dp, 0.dp, 0.dp)
                    .clip(RoundedCornerShape(100.dp))
            ) {
                Text(
                    text = stringResource(R.string.hide),
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    PurposeHeader {}
}