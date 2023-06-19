package com.example.newsapp.ui

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithTextStringId(
    @StringRes id: Int,
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithContentDescriptionStringId(
    @StringRes id: Int,
): SemanticsNodeInteraction = onNodeWithContentDescription(activity.getString(id))