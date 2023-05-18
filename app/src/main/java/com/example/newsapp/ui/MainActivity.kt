package com.example.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.navigation.Screen
import com.example.newsapp.ui.component.BottomBar
import com.example.newsapp.ui.component.NewsWebView
import com.example.newsapp.ui.screen.BookmarkScreen
import com.example.newsapp.ui.screen.DetailScreen
import com.example.newsapp.ui.screen.HomeScreen
import com.example.newsapp.ui.screen.ProfileScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.Utils

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
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NewsAppTheme {
        Scaffold(
            bottomBar = {
                if(
                    currentRoute == Screen.Home.route ||
                    currentRoute == Screen.Bookmark.route ||
                    currentRoute == Screen.Profile.route
                ){
                    BottomBar(
                        navController = navController
                    )
                }
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
                    HomeScreen(
                        navigateToDetailScreen = {newsId ->
                            navController.navigate(Screen.DetailNews.createRoute(newsId))
                        }
                    )
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
                composable(
                    route = Screen.DetailNews.route,
                    arguments = listOf(
                        navArgument("newsId"){
                            type = NavType.LongType
                        }
                    )
                ){
                    val id = it.arguments?.getLong("newsId") ?: -1L
                    DetailScreen(
                        newsId = id,
                        onBackPressed = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(top = 20.dp, start = 15.dp, end = 15.dp)
                            .fillMaxSize(),
                        navController = navController
                    )
                }
                composable(
                    route = Screen.FurtherReadingNews.route,
                    arguments = listOf(
                        navArgument("newsUrl"){
                            type = NavType.StringType
                        }
                    )
                ){
                    val encodedNewsUrl = it.arguments?.getString("newsUrl")
                    if(encodedNewsUrl != null){
                        val newsUrl = Utils.decodeStringUrl(encodedNewsUrl)
                        NewsWebView(
                            modifier = Modifier
                                .fillMaxSize(),
                            newsUrl = newsUrl
                        )
                    }
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