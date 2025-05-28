package com.gliskstudio.themoviedatabaseta.view.search.container

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.presentation.SharedViewModel
import com.gliskstudio.themoviedatabaseta.view.search.SearchScreen
import com.gliskstudio.themoviedatabaseta.view.sharedInstances.BackButton
import com.gliskstudio.themoviedatabaseta.view.theme.SurfaceContainerHigh

@Composable
fun StyledSearchBar(
    isSearchBarVisible: Boolean,
    controller: NavHostController
) {
    AnimatedVisibility(
        visible = isSearchBarVisible,
        enter = expandVertically(tween( 400)),
        exit = shrinkVertically(tween( 400))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp, 0.dp)
                .background(SurfaceContainerHigh, RoundedCornerShape(28.dp))
                .padding(16.dp, 4.dp)
        ) {
            val currentBackStack by controller.currentBackStackEntryAsState()
            val isButtonVisible = rememberSaveable(currentBackStack) {
                controller.previousBackStackEntry != null
            }

            val activity = LocalActivity.current as ComponentActivity
            val viewModel = hiltViewModel<SharedViewModel>(activity)

            val query = viewModel.queryTextState
            val queryState = query.collectAsState()
            val queryIsNotEmpty by remember {
                derivedStateOf {
                    queryState.value.isNotEmpty()
                }
            }

            var isValueChanged by rememberSaveable { mutableStateOf(false) }

            if (queryIsNotEmpty
                && isValueChanged
                && controller.currentDestination?.route != SearchScreen.route) {
                controller.navigate(SearchScreen.route)
            }

            isValueChanged = false

            BackButton(
                isButtonVisible
            ) {
                query.value = ""
                controller.popBackStack()
            }

            val onValueChange: (String) -> Unit = {
                isValueChanged = true
                query.value = it
            }

            SearchTextField(
                queryState,
                onValueChange = onValueChange,
                // #1 I used padding instead of arrangement to prevent jumping while back button appears
                // I am not using start padding due to internal TextField's padding (20dp)
                Modifier.weight(1f).padding(0.dp, 0.dp, 16.dp, 0.dp)
            )

            SearchButton(query) {
                if (controller.currentDestination?.route != SearchScreen.route) {
                    controller.navigate(SearchScreen.route)
                }
            }
        }
    }

}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    val controller = rememberNavController()
    StyledSearchBar(true, controller)
}