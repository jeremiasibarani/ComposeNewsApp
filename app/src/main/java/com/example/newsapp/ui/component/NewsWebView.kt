package com.example.newsapp.ui.component

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch

@Composable
fun NewsWebView(
    modifier : Modifier = Modifier,
    newsUrl : String? = null,
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
    ){
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                }
            },
            update = {webView ->
                coroutineScope.launch {
                    newsUrl?.let{ url ->
                        webView.loadUrl(url)
                    }
                }
            }
        )
    }
}