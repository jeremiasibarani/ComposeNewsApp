package com.example.newsapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.NewsViewModel
import com.example.newsapp.model.network.News
import com.example.newsapp.ui.component.NewsCard
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.newsapp.R
import com.example.newsapp.ViewModelFactory
import com.example.newsapp.di.Injector
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.ui.common.UiState
import com.example.newsapp.ui.component.SearchBar
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel : NewsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository(LocalContext.current))
    ),
    navigateToDetailScreen : (newsId : Long) -> Unit
){
    val context = LocalContext.current
    //Todo(handle loading and error state of the call)
    Column(
        modifier = modifier
    ){
        SearchBar(
            placeHolderText = "Search news",
            iconLeading = R.drawable.search_icon,
            onSearchClicked = {query ->
                viewModel.getNews(query)
            }
        )

        val news = viewModel.getNews().collectAsLazyPagingItems()
        NewsList(
            modifier = modifier,
            data = news,
            navigateToDetailScreen = navigateToDetailScreen
        )

//        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let{uiState ->
//            when(uiState){
//                is UiState.Loading -> {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f)
//                    ){
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                        )
//                    }
//
//                }
//                is UiState.Error -> {
//                    Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
//                }
//                is UiState.Success -> {
//                    NewsList(
//                        modifier = modifier,
//                        data = news
//                    )
//                }
//            }
//        }
    }

}

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    data : LazyPagingItems<NewsEntity>,
    navigateToDetailScreen : (newsId : Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        modifier = modifier
    ){
        itemsIndexed(data){_, news ->
            news?.let{
                NewsCard(
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                           navigateToDetailScreen(news.id)
                        },
                    news = it
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPrev() {
    NewsAppTheme {
        //HomeScreen()
    }
}