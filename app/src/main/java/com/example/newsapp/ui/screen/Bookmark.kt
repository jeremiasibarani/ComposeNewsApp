package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.BookmarkNewsViewModel
import com.example.newsapp.ViewModelFactory
import com.example.newsapp.di.Injector
import com.example.newsapp.model.database.BookmarkNewsEntity
import com.example.newsapp.ui.component.BookmarkNews
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel : BookmarkNewsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository(LocalContext.current))
    )
) {

    val bookmarkedNews by viewModel.getAllBookmarkedNews().collectAsStateWithLifecycle(initialValue = emptyList())

    BookmarkList(
        modifier = modifier
            .fillMaxSize(),
        data = bookmarkedNews,
        removedFromBookmarkAction = { bookmarkNews ->
            viewModel.deleteFromBookmark(bookmarkNews)
        }
    )
}

@Composable
fun BookmarkList(
    modifier: Modifier = Modifier,
    data : List<BookmarkNewsEntity>,
    removedFromBookmarkAction : (news : BookmarkNewsEntity) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ){
        LazyColumn(
            contentPadding = PaddingValues(10.dp)
        ){
            items(data){news ->
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