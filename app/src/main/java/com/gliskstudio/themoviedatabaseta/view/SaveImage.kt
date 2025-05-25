package com.gliskstudio.themoviedatabaseta.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.ui.theme.Black
import com.gliskstudio.themoviedatabaseta.ui.theme.White
import com.gliskstudio.themoviedatabaseta.ui.theme.White20

@Composable
fun SaveImage(
    id: Int,
    modifier: Modifier = Modifier,
    contrastRequired: Boolean = false
) {
    val contrastModifier = if(contrastRequired) {
        Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(White20)
            .border(0.5.dp, White, RoundedCornerShape(6.dp))
            .padding(4.dp)
    } else {
        Modifier
    }

    var isSaved by rememberSaveable { mutableStateOf(false) } // TODO Get from viewmodel

    Image(
        painter = painterResource(
            if (isSaved)
                R.drawable.ic_save_enabled
            else
                R.drawable.ic_save_disabled
        ),
        contentDescription = stringResource(R.string.save),
        colorFilter = ColorFilter.tint(Black, BlendMode.SrcIn),
        modifier = Modifier
            .clickable {
                isSaved = !isSaved
                // TODO Save with id
            }
            .then(contrastModifier)
            .then(modifier)
    )
}

@Preview
@Composable
private fun Preview() {
    SaveImage(1, Modifier.size(24.dp), true)
}