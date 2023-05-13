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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.model.network.News
import com.example.newsapp.navigation.Screen
import com.example.newsapp.ui.component.BookmarkNews
import com.example.newsapp.ui.component.BottomBar
import com.example.newsapp.ui.component.NewsCard
import com.example.newsapp.ui.component.SearchBar
import com.example.newsapp.ui.screen.*
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NewsAppTheme {
        Scaffold(
            bottomBar = {
                if(currentRoute != Screen.DetailNews.route){
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
                        }
                    )
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