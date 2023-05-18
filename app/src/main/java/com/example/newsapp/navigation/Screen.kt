package com.example.newsapp.navigation

import com.example.newsapp.util.Utils

sealed class Screen(val route : String){
    object Home : Screen("home")
    object Bookmark : Screen("bookmark")
    object Profile : Screen("profile")
    object DetailNews : Screen("home/{newsId}"){
        fun createRoute(newsId : Long) = "home/$newsId"
    }
    object FurtherReadingNews : Screen("furtherreadingnews/{newsUrl}"){
        fun createRoute(newsUrl : String) : String = "furtherreadingnews/${Utils.encodeStringUrl(newsUrl)}"
    }
}
