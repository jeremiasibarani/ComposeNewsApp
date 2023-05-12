package com.example.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.model.network.News
import com.example.newsapp.navigation.Screen
import com.example.newsapp.ui.component.BookmarkNews
import com.example.newsapp.ui.component.BottomBar
import com.example.newsapp.ui.component.NewsCard
import com.example.newsapp.ui.component.SearchBar
import com.example.newsapp.ui.screen.BookmarkScreen
import com.example.newsapp.ui.screen.HomeScreen
import com.example.newsapp.ui.screen.ProfileScreen
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsApp()
                }
            }
        }
    }
}

@Composable
fun NewsApp() {

    val context = LocalContext.current
    val navController = rememberNavController()


    NewsAppTheme {
        Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController
                )
            }
        ) {innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .padding(innerPadding)
            ){
                composable(
                    route = Screen.Home.route
                ){
                    HomeScreen()
                }
                composable(
                    route = Screen.Bookmark.route
                ){
                    BookmarkScreen()
                }
                composable(
                    route = Screen.Profile.route
                ){
                    ProfileScreen()
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsApp()
}