package com.example.newsapp.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newsapp.NewsDetailViewModel
import com.example.newsapp.model.network.News
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ViewModelFactory
import com.example.newsapp.di.Injector
import com.example.newsapp.model.database.NewsEntity

@Composable
fun DetailScreen(
    modifier : Modifier = Modifier,
    newsId : Long,
    viewModel : NewsDetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository(LocalContext.current))
    ),
    onBackPressed : () -> Unit
) {

    val detailNews by viewModel.getDetailNewsById(newsId).collectAsStateWithLifecycle(initialValue = NewsEntity())

    val context = LocalContext.current

    Log.i("DETAILSCREEN-TAG", "$detailNews")

    Column(
        modifier = modifier
    ) {
        DetailNews(
            modifier = Modifier
                .weight(1f),
            news = detailNews,
            onBookmarkClicked = {news ->
                viewModel.bookmarkNews(news)
                Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show()
            }
        )
        BottomBarAction(
            modifier = Modifier
                .wrapContentHeight()
        )
        BackHandler(
            enabled = true,
            onBack = onBackPressed
        )
    }
}


// Todo(resolve this erro : Parameter specified as non-null is null: method com.example.newsapp.ui.screen.DetailScreenKt.DetailNews, parameter news)
@Composable
fun DetailNews(
    modifier: Modifier = Modifier,
    news : NewsEntity,
    onBookmarkClicked : (news : NewsEntity) -> Unit
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
                    .clickable {
                        onBookmarkClicked(news)
                    }
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
            text = news.description,
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
            text = news.content,
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
//        DetailScreen(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(15.dp)
//        )
    }
}
