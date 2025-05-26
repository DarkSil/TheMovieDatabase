package com.gliskstudio.themoviedatabaseta.view.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.model.MovieItem
import com.gliskstudio.themoviedatabaseta.ui.theme.OnSurface
import com.gliskstudio.themoviedatabaseta.ui.theme.OnSurfaceVariant

@Composable
fun DetailsBody(item: MovieItem) {
    Column(
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp, 0.dp)
        ) {
            Text(
                text = item.releaseDate,
                fontSize = 12.sp,
                color = OnSurfaceVariant,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = stringResource(R.string.rating, item.rating),
                fontSize = 12.sp,
                color = OnSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
            )
        }

        Text(
            text = item.description,
            fontSize = 14.sp,
            color = OnSurface,
            // 64.dp padding due to overlapping button
            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 64.dp)
        )
    }
}