package com.example.newsapp.ui.screen

import android.widget.Toast
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
import com.example.newsapp.R
import com.example.newsapp.ViewModelFactory
import com.example.newsapp.di.Injector
import com.example.newsapp.ui.common.UiState
import com.example.newsapp.ui.component.SearchBar
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel : NewsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository())
    )
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

        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let{uiState ->
            when(uiState){
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }

                }
                is UiState.Error -> {
                    Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    NewsList(
                        modifier = modifier,
                        data = uiState.data.articles
                    )
                }
            }
        }
    }

}

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    data : List<News>
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        modifier = modifier
    ){
        items(data){news ->
            NewsCard(
                modifier = Modifier
                    .padding(10.dp),
                news = news
            )
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