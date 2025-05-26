package com.gliskstudio.themoviedatabaseta.view.sharedInstances

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel

@Composable
fun BaseUrlImage(
    context: Context,
    url: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val activity = LocalActivity.current as ComponentActivity
    val viewModel = hiltViewModel<SharedViewModel>(activity)

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .placeholder(R.drawable.placeholder_big)
            .error(R.drawable.placeholder_big)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        imageLoader = viewModel.imageLoader,
    )

}