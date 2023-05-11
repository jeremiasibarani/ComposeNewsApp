package com.example.newsapp.model.network

import com.google.gson.annotations.SerializedName

data class SearchNewsResponse(
    val status : String,
    val page : Int,
    @SerializedName("total_pages")
    val totalPages : Long,
    @SerializedName("page_size")
    val pageSize : Long,
    val articles : List<News>
)

data class News(
    val title : String = "",
    val author : String = "",
    @SerializedName("published_date")
    val publishedDate : String = "",
    @SerializedName("link")
    val furtherReadingLink : String = "",
    @SerializedName("clean_url")
    val source : String = "",
    val excerpt : String = "",
    val summary : String = "",
    val topic : String = "",
    val language : String = "",
    @SerializedName("media")
    val imgUrl : String = "",
    val country : String = ""
)

