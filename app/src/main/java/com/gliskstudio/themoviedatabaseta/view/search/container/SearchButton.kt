package com.gliskstudio.themoviedatabaseta.view.search.container

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.ui.theme.OnSurfaceVariant

@Composable
fun SearchButton(
    query: MutableState<String>,
    searchClick: () -> Unit
) {
    val isSearchButtonVisible = query.value.isEmpty()

    Box(
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isSearchButtonVisible,
            enter = fadeIn(tween(400, 200)),
            exit = fadeOut(tween(400))
        ) {
            Image(
                painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.search_button),
                colorFilter = ColorFilter.tint(OnSurfaceVariant),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = searchClick),
            )
        }
        AnimatedVisibility(
            visible = !isSearchButtonVisible,
            enter = fadeIn(tween(400, 200)),
            exit = fadeOut(tween(400))
        ) {
            Image(
                painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.clear_button),
                colorFilter = ColorFilter.tint(OnSurfaceVariant),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        query.value = ""
                    },
            )
        }
    }
}