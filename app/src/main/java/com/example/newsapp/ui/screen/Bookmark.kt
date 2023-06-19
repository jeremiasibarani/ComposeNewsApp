package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.R
import com.example.newsapp.di.Injector
import com.example.newsapp.model.database.BookmarkNewsEntity
import com.example.newsapp.ui.component.BookmarkNews
import com.example.newsapp.ui.component.DataNotFound
import com.example.newsapp.ui.component.SearchBar
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.viewmodel.BookmarkNewsViewModel
import com.example.newsapp.ui.viewmodel.ViewModelFactory
import com.example.newsapp.util.Constants.BOOKMARK_LIST_TEST_TAG

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel : BookmarkNewsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository(LocalContext.current))
    )
) {

    val bookmarkedNews by viewModel.bookmarkedNews.collectAsState()

    Column(
        modifier = modifier
    ) {
        SearchBar(
            placeHolderText = stringResource(id = R.string.search_placeholder),
            iconLeading = R.drawable.search_icon,
            onSearchClicked = {newsTitle ->
                viewModel.searchBookmarkedNews(newsTitle)
            },
            onClearSearchClicked = {
                viewModel.getAllBookmarkedNews()
            }
        )
        if(bookmarkedNews.isNotEmpty()){
            BookmarkList(
                modifier = Modifier
                    .fillMaxSize(),
                data = bookmarkedNews,
                removedFromBookmarkAction = { newsTitle ->
                    viewModel.deleteFromBookmark(newsTitle)
                }
            )
        }else{
            DataNotFound(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(id = R.string.no_data_found)
            )
        }
    }
}

@Composable
fun BookmarkList(
    modifier: Modifier = Modifier,
    data : List<BookmarkNewsEntity>,
    removedFromBookmarkAction : (newsTitle : String) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ){
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .testTag(BOOKMARK_LIST_TEST_TAG)
        ){
            items(data, key = {it.id}){news ->
                BookmarkNews(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    news = news,
                    removedFromBookmarkAction = removedFromBookmarkAction
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkListPrev() {
    NewsAppTheme{
        BookmarkList(
            data = listOf(
                BookmarkNewsEntity(
                    country = "Europe",
                    title = "Russian warship: Moskva sinks in Black Sea",
                    source = "BBC News",
                    publishedDate = "4 days ago"
                )
            ),
            modifier = Modifier.fillMaxSize(),
            removedFromBookmarkAction = {}
        )
    }
}