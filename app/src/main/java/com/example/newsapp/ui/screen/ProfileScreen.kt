package com.example.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R


@Composable
fun ProfileScreen(
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Profile()
    }
}

@Composable
fun Profile(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 13.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.profile_title),
                style = MaterialTheme.typography.h5.copy(
                    fontSize = 19.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.setting_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.profile_img),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.profile_name),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.profile_email),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        Text(
            text = stringResource(id = R.string.profile_description),
            style = MaterialTheme.typography.caption.copy(
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        Row(
            modifier = Modifier
                .wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.email_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.instagram_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.linkedin_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPrev() {
    MaterialTheme{
        ProfileScreen()
    }
}