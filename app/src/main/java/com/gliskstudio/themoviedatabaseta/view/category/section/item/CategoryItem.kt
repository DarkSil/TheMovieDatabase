package com.gliskstudio.themoviedatabaseta.view.category.section.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.model.MovieItem
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.sharedInstances.PlayPauseImage
import com.gliskstudio.themoviedatabaseta.view.sharedInstances.SaveImage
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurfaceVariant

@Composable
fun CategoryItem(
    item: MovieItem,
    onItemClick: (id: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .clickable {
                onItemClick(item.id)
            },
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val context = LocalContext.current

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(item.imageUrl)
                .crossfade(true)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .build(),
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Text(
                text = item.movieTitle,
                fontSize = 22.sp,
            )

            Text(
                text = item.description,
                fontSize = 14.sp,
                color = OnSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(0.dp, 4.dp)
            )

            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SaveImage(
                        id = item.id,
                        modifier = Modifier.size(24.dp),
                    )

                    Text(
                        text = stringResource(
                            R.string.date_and_rating,
                            item.releaseDate,
                            item.rating
                        ),
                        fontSize = 12.sp,
                        color = OnSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    PlayPauseImage(item)
                }
            }
        }
    }
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    CategoryItem(Utils.mockMovieItem()) {}
}