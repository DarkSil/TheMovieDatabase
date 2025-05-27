package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.domain.model.LoadingStatus
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurface
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CategoryListText(
    statusFlow: StateFlow<LoadingStatus>
) {
    val status by statusFlow.collectAsState()

    var text: String? = null
    var imageResource: Int? = null
    when (status) {
        LoadingStatus.EmptyQuery -> {
            text = stringResource(R.string.type_to_search)
            imageResource = R.drawable.ic_cursor
        }
        // TODO show fullscreen only when the search list is empty
        is LoadingStatus.Error -> {
            val errorCode = (status as LoadingStatus.Error).errorCode
            text = stringResource(R.string.oops_message, errorCode)
            imageResource = null
        }
        is LoadingStatus.Loaded -> {
            val isEmpty = (status as LoadingStatus.Loaded).list.isEmpty()
            if (isEmpty) {
                text = stringResource(R.string.nothing_found)
                imageResource = R.drawable.ic_nothing_found
            } else {
                text = null
                imageResource = null
            }
        }
        else -> {
            text = null
            imageResource = null
        }
    }

    AnimatedVisibility(
        visible = text != null,
        enter = fadeIn(tween(200, 200)),
        exit = fadeOut(tween(200))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = text ?: "",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = OnSurface,
                modifier = Modifier
                    .weight(1f)
            )

            imageResource?. let {
                Image(
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(28.dp)
                )
            }
        }
    }
}