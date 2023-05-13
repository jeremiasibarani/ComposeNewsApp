package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.BookmarkNewsViewModel
import com.example.newsapp.ui.component.BookmarkNews
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ViewModelFactory
import com.example.newsapp.di.Injector
import com.example.newsapp.model.database.BookmarkNewsEntity
import com.example.newsapp.model.database.NewsEntity

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel : BookmarkNewsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository(LocalContext.current))
    )
) {

    val bookmarkedNews by viewModel.getAllBookmarkedNews().collectAsStateWithLifecycle(initialValue = emptyList())

    BookmarkList(
        modifier = modifier,
        data = bookmarkedNews
    )
}

@Composable
fun BookmarkList(
    modifier: Modifier = Modifier,
    //Todo(decide whether or not to separate the type of this class, because this class will be the one retrieved from database and currently it's using the one from response)
    data : List<BookmarkNewsEntity>
) {
    Box(modifier = modifier){
        LazyColumn(
            contentPadding = PaddingValues(10.dp)
        ){
            items(data){news ->
                BookmarkNews(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    news = news
                )
            }
        }
    }
}