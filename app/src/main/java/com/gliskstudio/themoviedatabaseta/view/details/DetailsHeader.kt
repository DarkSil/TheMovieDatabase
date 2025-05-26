package com.gliskstudio.themoviedatabaseta.view.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.model.MovieItem
import com.gliskstudio.themoviedatabaseta.ui.theme.Black25
import com.gliskstudio.themoviedatabaseta.ui.theme.Black50
import com.gliskstudio.themoviedatabaseta.ui.theme.White
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.sharedInstances.BackButton
import com.gliskstudio.themoviedatabaseta.view.sharedInstances.SaveImage

@Composable
fun DetailsHeader(
    item: MovieItem,
    paddingTop: Dp,
    onBackPressed: () -> Unit
) {
    ConstraintLayout {
        val (image, back, title) = createRefs()
        val context = LocalContext.current

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(item.imageUrl)
                .placeholder(R.drawable.placeholder_big)
                .error(R.drawable.placeholder_big)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4.1f/3.5f)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                }
        )

        BackButton(
            isVisible = true,
            isBackgroundRequired = true,
            modifier = Modifier
                .constrainAs(back) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                // PaddingTop contains top insets padding so I can place the button
                .padding(12.dp, 12.dp + paddingTop),
            onBackPressed = onBackPressed
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    bottom.linkTo(parent.bottom)
                }
                .background(Brush.verticalGradient(
                    0.0f to Color.Transparent,
                    0.36f to Black25,
                    1f to Black50
                ))
                .padding(16.dp, 16.dp)
        ){
            Text(
                text = item.movieTitle,
                fontSize = 36.sp,
                color = White,
                modifier = Modifier
                    .weight(1f)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DownloadButton {
                    // TODO Download video
                }

                SaveImage(
                    id = item.id,
                    contrastRequired = true
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DetailsHeader(Utils.mockMovieItem(), 0.dp) {}
}