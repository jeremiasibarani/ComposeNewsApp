package com.example.newsapp.model.network

import com.google.gson.annotations.SerializedName

data class SearchNewsResponse(
    val status : String,
    val totalResults : Long,
    val articles : List<News>
)

data class Source(
    val id : String?,
    val name : String
)

data class News(
    val source : Source,
    val author: String,
    val title : String,
    val description : String,
    @SerializedName("url")
    val furtherReadingLink: String,
    @SerializedName("urlToImage")
    val imgUrl : String,
    @SerializedName("publishedAt")
    val publishedDate : String,
    val content : String
)

/*
    for news card we need :
    - image url (imgUrl)
    - country
    - source
    - title
    - publish date

    for detail news we need above with extra :
    - excerpt
    - summary

    therefore our new news will be :
    for news card :
    -
 */

