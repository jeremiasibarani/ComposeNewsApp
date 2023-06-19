package com.example.newsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.model.database.BookmarkNewsEntity
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun BookmarkNews(
    modifier : Modifier = Modifier,
    news : BookmarkNewsEntity,
    removedFromBookmarkAction : (newsTitle : String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 2.dp,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = news.imgUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(96.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 6.dp, bottomStart = 6.dp)),
                placeholder = painterResource(id = R.drawable.news_placeholder),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(top = 4.dp, bottom = 5.dp, end = 4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = news.country,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bookmark_filled),
                        contentDescription = stringResource(id = R.string.bookmark_icon_content_description),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                removedFromBookmarkAction(news.title)
                            }
                    )
                }


                Text(
                    text = news.title,
                    style = MaterialTheme.typography.h5
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
}

@Preview
@Composable
fun BookmarkNewsPrev() {
    NewsAppTheme{
        BookmarkNews(
            news = BookmarkNewsEntity(
                country = "Europe",
                title = "Russian warship: Moskva sinks in Black Sasdasdasdadasdasdasdadwqwdqwdqwea",
                source = "BBC News",
                publishedDate = "4 days ago",
                imgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/96/INS_Kamorta_during_trials_at_sea.jpg"
            ),
            removedFromBookmarkAction = {}
        )
    }
}