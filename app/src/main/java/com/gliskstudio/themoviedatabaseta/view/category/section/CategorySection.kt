package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.model.CategoryType
import com.gliskstudio.themoviedatabaseta.model.LoadStatus
import com.gliskstudio.themoviedatabaseta.ui.theme.OnSurface
import com.gliskstudio.themoviedatabaseta.utils.Utils

@Composable
fun CategorySection(
    categoryType: CategoryType,
    status: LoadStatus,
    onCategoryClick: (categoryType: CategoryType) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CategoryTitle(
            categoryType,
            onCategoryClick
        )

        // I decided to play with AnimatedVisibilities. It increased the code size, but looks cool

        // TODO Load items list with ViewModel
        AnimatedVisibility(
            visible = status is LoadStatus.Loaded && status.list.isNotEmpty(),
            enter = fadeIn(tween(200, 100)),
            exit = fadeOut(tween(200))
        ) {
            CategoryList(
                if (status is LoadStatus.Loaded) status.list else emptyList(),
                categoryType.isLimited,
                onItemClick
            )
        }

        AnimatedVisibility(
            visible = status is LoadStatus.InProgress,
            enter = fadeIn(tween(200, 200)),
            exit = fadeOut(tween(200))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 24.dp)
            ) {
                // TODO Custom
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        AnimatedVisibility(
            visible = status is LoadStatus.EmptyQuery
                    || (status is LoadStatus.Loaded && status.list.isEmpty())
                    || status is LoadStatus.Error,
            enter = fadeIn(tween(200, 200)),
            exit = fadeOut(tween(200))
        ) {
            val text: String
            val imageResource: Int?
            when (status) {
                LoadStatus.EmptyQuery -> {
                    text = stringResource(R.string.type_to_search)
                    imageResource = R.drawable.ic_cursor
                }
                is LoadStatus.Error -> {
                    text = stringResource(R.string.oops_message, status.errorCode)
                    imageResource = null
                }
                else -> {
                    text = stringResource(R.string.nothing_found)
                    imageResource = R.drawable.ic_nothing_found
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = text,
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
}

@Preview (apiLevel = 34)
@Composable
private fun Preview() {
    Scaffold { padding ->
        CategorySection(
            categoryType = CategoryType.Featured(true),
            status = LoadStatus.Loaded(Utils.mockMovieList()),
            onCategoryClick = {},
            onItemClick = {}
        )
    }
}