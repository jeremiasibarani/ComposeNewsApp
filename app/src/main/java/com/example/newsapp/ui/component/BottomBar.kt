package com.example.newsapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.NavigationItem
import com.example.newsapp.R
import com.example.newsapp.navigation.Screen
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun BottomBar(
    modifier : Modifier = Modifier,
    navController: NavHostController
) {
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(id = R.string.menu_home),
            icon = R.drawable.home_icon,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(id = R.string.menu_bookmark),
            icon = R.drawable.bookmark_icon,
            screen = Screen.Bookmark
        ),
        NavigationItem(
            title = stringResource(id = R.string.menu_profile),
            icon = R.drawable.profile_icon,
            screen = Screen.Profile
        ),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    BottomNavigation(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        navigationItems.map { item ->
            BottomNavigationItem(
                icon = {
                    Icon(painter = painterResource(id = item.icon), contentDescription = null)
                },
                label = {
                    Text(
                        text = item.title
                    )
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPrev() {
    NewsAppTheme {
        BottomBar(navController = rememberNavController())
    }
}