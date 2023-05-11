package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.network.News
import com.example.newsapp.ui.component.NewsCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    NewsList(
        modifier = modifier,
        data = listOf(
            News(
                country = "Europe",
                title = "Russian warship: Moskva sinks in Black Sea",
                source = "BBC News",
                publishedDate = "4 days ago",
                imgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/96/INS_Kamorta_during_trials_at_sea.jpg"
            ),
            News(
                country = "Europe",
                title = "Russian warship: Moskva sinks in Black Sea",
                source = "BBC News",
                publishedDate = "4 days ago",
                imgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/96/INS_Kamorta_during_trials_at_sea.jpg"
            ),
            News(
                country = "Europe",
                title = "Russian warship: Moskva sinks in Black Sea",
                source = "BBC News",
                publishedDate = "4 days ago",
                imgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/96/INS_Kamorta_during_trials_at_sea.jpg"
            ),
            News(
                country = "Europe",
                title = "Russian warship: Moskva sinks in Black Sea",
                source = "BBC News",
                publishedDate = "4 days ago",
                imgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/96/INS_Kamorta_during_trials_at_sea.jpg"
            ),
        )
    )
}

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    data : List<News>
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ){
        LazyColumn(
            contentPadding = PaddingValues(10.dp)
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
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPrev() {
    MaterialTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}