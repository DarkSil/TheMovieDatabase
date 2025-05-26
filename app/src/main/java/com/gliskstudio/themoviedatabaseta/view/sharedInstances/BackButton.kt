package com.gliskstudio.themoviedatabaseta.view.sharedInstances

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.ui.theme.OnSurfaceVariant
import com.gliskstudio.themoviedatabaseta.ui.theme.SecondaryContainer

@Composable
fun BackButton(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    isBackgroundRequired: Boolean = false,
    onBackPressed: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandHorizontally(tween(400)),
        exit = shrinkHorizontally(tween(400)),
        modifier = modifier
    ) {
        Image(
            painterResource(R.drawable.ic_forward),
            contentDescription = stringResource(R.string.back_button),
            colorFilter = ColorFilter.tint(OnSurfaceVariant),
            modifier = Modifier
                .then(
                    if (isBackgroundRequired) {
                        Modifier
                            .size(32.dp, 40.dp)
                            .background(SecondaryContainer, RoundedCornerShape(20.dp))
                            .padding(4.dp, 8.dp)
                    } else {
                        Modifier
                            .size(24.dp)
                    }
                )
                .rotate(180f)
                .clickable(onClick = onBackPressed),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    BackButton(true) {}
}