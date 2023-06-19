package com.example.newsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.ui.theme.NewsAppTheme



@Composable
fun NewsCard(
    modifier : Modifier = Modifier,
    news : NewsEntity
){
    Card(
        modifier = modifier
        .widthIn(250.dp)
        .wrapContentHeight()
        .padding(bottom = 10.dp),
        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp),
        elevation = 1.dp
    ) {
        Column{
            AsyncImage(
                model = news.imgUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.news_placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(6.dp))

            )
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 7.dp),
                text = news.country,
                style = MaterialTheme.typography.h6
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 7.dp),
                text = news.title,
                style = MaterialTheme.typography.h5
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 7.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_news_img),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = news.source,
                    modifier = Modifier.padding(start = 5.dp, end = 12.dp),
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Image(
                    painter = painterResource(id = R.drawable.time_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = news.publishedDate,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    NewsAppTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)) {
            NewsCard(
                news = NewsEntity(
                    country = "Europe",
                    title = "Russian warship: Moskva sinks in Black Sea",
                    source = "BBC News",
                    publishedDate = "4 days ago"
                )
            )
        }
    }
}
