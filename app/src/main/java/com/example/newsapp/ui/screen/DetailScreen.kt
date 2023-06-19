package com.example.newsapp.ui.screen

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.di.Injector
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.navigation.Screen
import com.example.newsapp.ui.component.ProgressBar
import com.example.newsapp.ui.viewmodel.NewsDetailViewModel
import com.example.newsapp.ui.viewmodel.ViewModelFactory
import com.example.newsapp.util.Constants.DETAIL_NEWS_BUTTON_READ_ORIGIN_TEST_TAG


@Composable
fun DetailScreen(
    modifier : Modifier = Modifier,
    newsId : Long,
    viewModel : NewsDetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(Injector.provideNewsRepository(LocalContext.current))
    ),
    navController: NavHostController,
    onBackPressed : () -> Unit
) {

    val detailNews by viewModel.getDetailNewsById(newsId).collectAsStateWithLifecycle(initialValue = NewsEntity())

    Log.i("DETAILSCREEN-TAG", "$detailNews")

    if(detailNews.id != 0L){
        val isNewsBookmarked by viewModel.checkIfNewsExistsInBookmark(detailNews.title).collectAsState(initial = false)
        Log.i("DETAILSCREEN-TAG", "status bookmark : $isNewsBookmarked")
        DetailNews(
            modifier = modifier,
            navController = navController,
            onBackPressed = onBackPressed,
            viewModel = viewModel,
            news = detailNews,
            isNewsBookmarked = isNewsBookmarked
        )
    }else{
        ProgressBar(modifier = Modifier.fillMaxSize())
    }

}


@Composable
fun DetailNews(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onBackPressed : () -> Unit,
    viewModel: NewsDetailViewModel,
    news : NewsEntity,
    isNewsBookmarked : Boolean
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(top = 20.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize()
    ) {
        DetailNewsPage(
            modifier = Modifier
                .weight(1f),
            news = news,
            onBookmarkClicked = {news, isBookmark ->
                if(isBookmark){
                    viewModel.deleteNewsFromBookmark(news.title)
                }else{
                    viewModel.bookmarkNews(news)
                }
            },
            isNewsBookmarked = isNewsBookmarked
        )
        BottomBarAction(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .wrapContentHeight(),
            onClickReadOriginButton = {
                navController.navigate(
                    Screen.FurtherReadingNews.createRoute(news.furtherReadingLink)
                )
            },
            onShareClicked = {
                shareNews(context, news.title, news.description)
            }
        )

        BackHandler(
            enabled = true,
            onBack = onBackPressed
        )
    }

}


@Composable
fun DetailNewsPage(
    modifier: Modifier = Modifier,
    news : NewsEntity,
    onBookmarkClicked : (news : NewsEntity, bookmarkStatus : Boolean) -> Unit,
    isNewsBookmarked: Boolean
) {
    Log.i("DETAILSCREEN-TAG", "status bookmark-lowest : $isNewsBookmarked")
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
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
                painter = painterResource(
                    id = if(isNewsBookmarked) R.drawable.bookmark_icon_filled else R.drawable.ic_bookmark_outlined
                ),
                contentDescription = stringResource(id = R.string.bookmark_icon_content_description),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onBookmarkClicked(news, isNewsBookmarked)
                    },
                tint = MaterialTheme.colors.primary
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
                modifier = Modifier
                    .testTag(DETAIL_NEWS_BUTTON_READ_ORIGIN_TEST_TAG)
            ) {
                Text(
                    text = stringResource(id = R.string.read_origin),
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

private fun shareNews(context : Context, title : String, desc : String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, desc)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            "news"
        )
    )
}

