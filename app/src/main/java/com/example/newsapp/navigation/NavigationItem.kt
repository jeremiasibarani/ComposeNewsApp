package com.example.newsapp.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title : String,
    @DrawableRes val icon : Int,
    val screen : Screen
)
