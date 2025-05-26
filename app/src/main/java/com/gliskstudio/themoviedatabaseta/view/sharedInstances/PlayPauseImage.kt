package com.gliskstudio.themoviedatabaseta.view.sharedInstances

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.model.MovieItem
import com.gliskstudio.themoviedatabaseta.utils.Utils
import com.gliskstudio.themoviedatabaseta.view.theme.Black

@Composable
fun PlayPauseImage(item: MovieItem) {

    var isPlaying by rememberSaveable { mutableStateOf(false) }

    Image(
        painter = painterResource(
            if (isPlaying) {
                R.drawable.ic_pause
            } else {
                R.drawable.ic_play
            }
        ),
        contentDescription = if (isPlaying) {
            stringResource(R.string.pause_button)
        } else {
            stringResource(R.string.play_button)
        },
        colorFilter = ColorFilter.tint(Black),
        modifier = Modifier
            .size(24.dp)
            .clickable {
                // TODO Play/Pause via viewmodel
                isPlaying = !isPlaying
            }
    )

}

@Preview
@Composable
private fun Preview() {
    PlayPauseImage(Utils.mockMovieItem())
}