package com.gliskstudio.themoviedatabaseta.view.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.utils.Utils

object DetailsScreen {
    const val detailsArgument = "detailsId"
    const val route = "details/{$detailsArgument}"

    fun prepareRoute(detailsId: Int): String {
        return "details/$detailsId"
    }
}

@Composable
fun DetailsScreen(
    id: Int,
    paddingTop: Dp,
    controller: NavHostController
) {
    // TODO Get MovieItem by ID
    val item = Utils.mockMovieItem()

    val scrollState = rememberScrollState()

    ConstraintLayout {
        val button = createRef()

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            DetailsHeader(item, paddingTop) {
                controller.popBackStack()
            }
            DetailsBody(item)
        }

        Button(
            onClick = {
                // TODO Navigate to Watch Screen
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                }
                .padding(16.dp, 0.dp, 16.dp, 8.dp)
        ) {
            Text(
                text = stringResource(R.string.watch),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    val controller = rememberNavController()
    Scaffold { padding ->
        DetailsScreen(0, padding.calculateTopPadding(), controller)
    }
}