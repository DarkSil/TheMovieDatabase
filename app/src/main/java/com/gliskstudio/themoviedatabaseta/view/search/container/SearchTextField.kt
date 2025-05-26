package com.gliskstudio.themoviedatabaseta.view.search.container

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurface
import com.gliskstudio.themoviedatabaseta.view.theme.OnSurfaceVariant
import com.gliskstudio.themoviedatabaseta.view.theme.Primary

@Composable
fun SearchTextField(
    query: State<String>,
    onValueChange: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query.value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Search for movies",
                fontSize = 16.sp,
                color = OnSurfaceVariant
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = Primary,
            focusedTextColor = OnSurface,
            unfocusedTextColor = OnSurface,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        maxLines = 1,
        modifier = Modifier
            .then(modifier)
    )
}

@Preview(apiLevel = 34)
@Composable
private fun Preview() {
    val state = remember { mutableStateOf("") }
    SearchTextField(state, {})
}