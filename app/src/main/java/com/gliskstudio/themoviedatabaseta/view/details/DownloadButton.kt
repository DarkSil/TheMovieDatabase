package com.gliskstudio.themoviedatabaseta.view.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurface
import com.gliskstudio.themoviedatabaseta.view.theme.SurfaceContainerLow

@Composable
fun DownloadButton(
    click: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceContainerLow,
            contentColor = OnSurface
        ),
        modifier = Modifier
            .clickable(onClick = click)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp, 6.dp, 16.dp, 6.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_download),
                contentDescription = stringResource(R.string.download),
                modifier = Modifier
                    .size(18.dp)
            )

            Text(
                text = stringResource(R.string.download),
                fontSize = 14.sp
            )
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    DownloadButton {}
}