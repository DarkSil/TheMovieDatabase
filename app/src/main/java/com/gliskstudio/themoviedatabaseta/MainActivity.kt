package com.gliskstudio.themoviedatabaseta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gliskstudio.themoviedatabaseta.ui.theme.TheMovieDatabaseTATheme
import com.gliskstudio.themoviedatabaseta.view.search.container.SearchContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMovieDatabaseTATheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val controller = rememberNavController()
                    Base(innerPadding, controller)
                }
            }
        }
    }
}

@Composable
private fun Base(padding: PaddingValues, controller: NavHostController) {
    SearchContainer(
        controller = controller,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        AppNavigation(controller)
    }
}

@Preview(
    showBackground = true,
    apiLevel = 34
)
@Composable
fun GreetingPreview() {
    TheMovieDatabaseTATheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val controller = rememberNavController()
            Base(innerPadding, controller)
        }
    }
}