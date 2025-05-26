package com.gliskstudio.themoviedatabaseta.view.category.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.R
import com.gliskstudio.themoviedatabaseta.model.CategoryType
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurfaceVariant

@Composable
fun CategoryTitle(
    categoryType: CategoryType,
    onCategoryClick: (categoryType: CategoryType) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp)
            .clickable {
                onCategoryClick(categoryType)
            }
    ) {
        Text(
            text = stringResource(categoryType.categoryTitleId),
            fontSize = 24.sp
        )
        if (categoryType.isLimited) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_forward),
                    contentDescription = stringResource(R.string.forward_button),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(OnSurfaceVariant),
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CategoryTitle(CategoryType.Featured()) {}
}