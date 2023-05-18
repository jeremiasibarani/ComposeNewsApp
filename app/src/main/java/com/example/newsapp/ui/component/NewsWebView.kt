package com.example.newsapp.ui.component

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun NewsWebView(
    modifier : Modifier = Modifier,
    newsUrl : String? = null,
) {
    Box(
        modifier = modifier
    ){
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    newsUrl?.let{url ->
                        loadUrl(url)
                    }
                }
            },
            update = {webView ->
                newsUrl?.let{
                    webView.loadUrl(it)
                }
            }
        )
    }
}