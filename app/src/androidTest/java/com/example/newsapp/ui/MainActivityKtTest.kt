package com.example.newsapp.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.pressBack
import com.example.newsapp.R
import com.example.newsapp.navigation.Screen
import com.example.newsapp.util.Constants.BOOKMARK_LIST_TEST_TAG
import com.example.newsapp.util.Constants.DETAIL_NEWS_BUTTON_READ_ORIGIN_TEST_TAG
import com.example.newsapp.util.Constants.HOME_LAZY_COLUMN_TEST_TAG
import com.example.newsapp.util.Constants.SEARCH_BAR_TEST_TAG
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController : TestNavHostController
    private val notFoundKeyWord = "2-301-230123-120;pao"

    @Before
    fun setUp(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NewsApp(navController = navController)
        }
    }

    /*
    Skenario testing :
    1. Halaman home
        - Memastikan halaman home adalah halaman pertama yang tampil saat aplikasi dibuka
        - Memastikan saat hasil pencarian tidak ditemukan menampilkan pesan tidak ada data (no data found)
        - Memastikan saat salah satu item ditekan, halaman detailnews tampil
     */
    @Test
    fun verifyHomeAsStartDestination(){
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun searchNotExistDisplayDataNotFound(){
        composeTestRule.apply{
            waitForIdle()
            onNodeWithTag(SEARCH_BAR_TEST_TAG)
                .performTextInput(notFoundKeyWord)
            waitForIdle()
            onNodeWithTag(SEARCH_BAR_TEST_TAG)
                .performImeAction()
            waitForIdle()
            onNodeWithTextStringId(
                R.string.no_data_found
            ).assertIsDisplayed()
        }
    }


    @Test
    fun itemClickedNavigateToDetailNews(){
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(HOME_LAZY_COLUMN_TEST_TAG)
            .performScrollToIndex(4)
            .performClick()
        navController.assertCurrentRouteName(Screen.DetailNews.route)
    }

    /*
    Skenario testing :
    2. Halaman detail
        - Memastikan saat tombol read origin ditekan, halaman further reading tampil dan
          memastikan saat tombol back stack benar saat menekan tombol back system sampai ke halaman home
     */

    @Test
    fun readOriginButtonClickedFurtherReadingScreenDisplayedAndPressBackUntilHome(){
        composeTestRule.apply{
            waitForIdle()
            onNodeWithTag(HOME_LAZY_COLUMN_TEST_TAG)
                .performScrollToIndex(0)
                .performClick()
            navController.assertCurrentRouteName(Screen.DetailNews.route)
            waitForIdle()
            onNodeWithTag(DETAIL_NEWS_BUTTON_READ_ORIGIN_TEST_TAG)
                .assertIsDisplayed()
                .performClick()
        }
        navController.assertCurrentRouteName(Screen.FurtherReadingNews.route)
        pressBack()
        navController.assertCurrentRouteName(Screen.DetailNews.route)
        pressBack()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    /*
    Skenario testing :
    3. Halaman bookmark
        - Memastikan proses bookmark berhasil
        - Memastikan saat hasil pencarian tidak ditemukan menampilkan pesan tidak ada data (no data found)
     */
    @Test
    fun addNewsToBookmark(){
        composeTestRule.apply{
            waitForIdle()
            onNodeWithTag(HOME_LAZY_COLUMN_TEST_TAG)
                .performScrollToIndex(0)
                .performClick()
            waitForIdle()
            onNodeWithContentDescriptionStringId(
                R.string.bookmark_icon_content_description
            ).performClick()
            pressBack()
            onNodeWithTag(Screen.Bookmark.route)
                .performClick()
            waitForIdle()
            onNodeWithTag(BOOKMARK_LIST_TEST_TAG).assertIsDisplayed()
            onNodeWithTag(SEARCH_BAR_TEST_TAG)
                .performTextInput(notFoundKeyWord)
            onNodeWithTag(SEARCH_BAR_TEST_TAG)
                .performImeAction()
            waitForIdle()
            onNodeWithTextStringId(
                R.string.no_data_found
            ).assertIsDisplayed()
            onNodeWithContentDescriptionStringId(
                R.string.clear_search_bar
            ).performClick()
            waitForIdle()
            onNodeWithContentDescriptionStringId(
                R.string.bookmark_icon_content_description
            ).performClick()
        }
    }

    /*
    Skenario testing :
    4. Memastikan menu bottom navigation menampilkan halaman yang benar
     */

    @Test
    fun verifyBottomNavDisplayTheCorrectMenu(){
        composeTestRule.apply{
            waitForIdle()
            navController.assertCurrentRouteName(Screen.Home.route)
            onNodeWithTag(HOME_LAZY_COLUMN_TEST_TAG)
                .performScrollToIndex(0)
                .performClick()
            navController.assertCurrentRouteName(Screen.DetailNews.route)
            waitForIdle()
            onNodeWithTag(DETAIL_NEWS_BUTTON_READ_ORIGIN_TEST_TAG)
                .assertIsDisplayed()
                .performClick()
            navController.assertCurrentRouteName(Screen.FurtherReadingNews.route)
            pressBack()
            navController.assertCurrentRouteName(Screen.DetailNews.route)
            pressBack()
            navController.assertCurrentRouteName(Screen.Home.route)
            onNodeWithTag(Screen.Bookmark.route)
                .performClick()
            navController.assertCurrentRouteName(Screen.Bookmark.route)
            onNodeWithTag(Screen.Profile.route)
                .performClick()
            navController.assertCurrentRouteName(Screen.Profile.route)
            pressBack()
            navController.assertCurrentRouteName(Screen.Home.route)
        }
    }


}