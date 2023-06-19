package com.example.newsapp.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.R
import com.example.newsapp.ui.viewmodel.ViewModelFactory
import com.example.newsapp.di.Injector
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.ui.component.DataNotFound
import com.example.newsapp.ui.component.NewsCard
import com.example.newsapp.ui.component.ProgressBar
import com.example.newsapp.ui.component.SearchBar
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.Constants.HOME_LAZY_COLUMN_TEST_TAG

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel : NewsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository(LocalContext.current))
    ),
    navigateToDetailScreen : (newsId : Long) -> Unit
){

    val news = viewModel.requestNews.collectAsLazyPagingItems()
    Column(
        modifier = modifier
    ){
        SearchBar(
            placeHolderText = stringResource(id = R.string.search_placeholder),
            iconLeading = R.drawable.search_icon,
            onSearchClicked = {query ->
                viewModel.getNews(query)
            }
        )

        if(news.itemCount > 0 || news.loadState.refresh is LoadState.Loading){
            NewsList(
                modifier = Modifier
                    .fillMaxSize(),
                data = news,
                navigateToDetailScreen = navigateToDetailScreen
            )
        }else{
            DataNotFound(
                text = stringResource(id = R.string.no_data_found),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }

}

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    data : LazyPagingItems<NewsEntity>,
    navigateToDetailScreen : (newsId : Long) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .testTag(HOME_LAZY_COLUMN_TEST_TAG)
        ){
            itemsIndexed(data){_, news ->
                Log.i("HomeScreen-TAG", "$news")
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
        if(
            data.loadState.refresh is LoadState.Loading ||
            data.loadState.append is LoadState.Loading ||
            data.loadState.prepend is LoadState.Loading
        ){
            ProgressBar(
                modifier = Modifier
                    .fillMaxSize()
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