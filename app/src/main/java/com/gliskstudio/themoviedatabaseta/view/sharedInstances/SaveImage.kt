package com.gliskstudio.themoviedatabaseta.view.sharedInstances

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel
import com.gliskstudio.themoviedatabaseta.view.theme.Black
import com.gliskstudio.themoviedatabaseta.view.theme.White
import com.gliskstudio.themoviedatabaseta.view.theme.White20

@Composable
fun SaveImage(
    id: Int,
    modifier: Modifier = Modifier,
    contrastRequired: Boolean = false
) {
    val activity = LocalActivity.current as ComponentActivity
    val sharedViewModel = hiltViewModel<SharedViewModel>(activity)

    val contrastModifier = if(contrastRequired) {
        Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(White20)
            .border(0.5.dp, White, RoundedCornerShape(6.dp))
            .padding(4.dp)
    } else {
        Modifier
    }

    val isSaved by sharedViewModel.isLiked(id).collectAsState(null)
    val fillColor = if (contrastRequired) White else Black

    Image(
        painter = painterResource(
            if (isSaved == true)
                R.drawable.ic_save_enabled
            else
                R.drawable.ic_save_disabled
        ),
        contentDescription = stringResource(R.string.save),
        colorFilter = ColorFilter.tint(fillColor, BlendMode.SrcIn),
        modifier = Modifier
            .clickable {
                sharedViewModel.triggerLiked(id)
            }
            .then(contrastModifier)
            .then(modifier)
    )
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    SaveImage(1, Modifier.size(24.dp), true)
}