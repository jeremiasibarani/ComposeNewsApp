package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.network.News
import com.example.newsapp.ui.component.BookmarkNews

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier
) {
    BookmarkList(
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
fun BookmarkList(
    modifier: Modifier = Modifier,
    //Todo(decide whether or not to separate the type of this class, because this class will be the one retrieved from database and currently it's using the one from response)
    data : List<News>
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