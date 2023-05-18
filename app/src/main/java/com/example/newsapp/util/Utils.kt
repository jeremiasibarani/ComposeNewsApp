package com.example.newsapp.util

import java.net.URLDecoder
import java.net.URLEncoder

object Utils {
    fun encodeStringUrl(url: String): String = URLEncoder.encode(url, "UTF-8")
    fun decodeStringUrl(encodedUrl : String) : String = URLDecoder.decode(encodedUrl, "UTF-8")
}