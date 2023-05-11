package com.example.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.model.network.News
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun DetailScreen(
    modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DetailNews(
            modifier = Modifier
                .weight(1f),
            news = news
        )
        BottomBarAction(
            modifier = Modifier
                .wrapContentHeight()
        )
    }
}

@Composable
fun DetailNews(
    modifier: Modifier = Modifier,
    news : News
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.bbc_news_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(start = 5.dp)
            ) {
                Text(
                    text = news.source,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = news.publishedDate,
                    style = MaterialTheme.typography.h6
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.bookmark_icon_filled),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        AsyncImage(
            model = news.imgUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .widthIn(min = 250.dp, max = 500.dp)
                .height(250.dp),
            placeholder = painterResource(id = R.drawable.news_placeholder),
            contentScale = ContentScale.Crop
        )

        Text(
            text = news.country,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Text(
            text = news.excerpt,
            style = MaterialTheme.typography.h5.copy(
                fontSize = 24.sp
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Text(
            text = news.summary,
            style = MaterialTheme.typography.h6.copy(
                fontSize = 15.sp
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun BottomBarAction(
    modifier : Modifier = Modifier,
    onClickReadOriginButton : () -> Unit = {},
    onShareClicked : () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)){
            Button(
                onClick = onClickReadOriginButton,
            ) {
                Text(
                    text = "Read Origin",
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.share_icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onShareClicked)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPrev() {
    NewsAppTheme {
        DetailScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        )
    }
}

private val news = News(
    source = "BBC News",
    publishedDate = "4 days ago",
    country = "Europe",
    excerpt = "Ukraine's President Zelensky to BBC: Blood money being paid for Russian oil",
    summary = "Ukrainian President Volodymyr Zelensky has accused European countries that continue to buy Russian oil of \"earning their money in other people's blood\".\n" +
            "\n" +
            "In an interview with the BBC, President Zelensky singled out Germany and Hungary, accusing them of blocking efforts to embargo energy sales, from which Russia stands to make up to £250bn (\$326bn) this year.\n"
)