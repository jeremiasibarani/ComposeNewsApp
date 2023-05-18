package com.example.newsapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "bookmarked_news")
data class BookmarkNewsEntity(
    @PrimaryKey
    val id : Long = 0L,
    val title : String = "",
    val author : String = "",
    val publishedDate : String = "",
    val furtherReadingLink : String = "",
    val source : String = "",
    val description : String = "",
    val content : String = "",
    val imgUrl : String = "",
    val country : String = "EN"
)

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val title : String = "",
    val author : String = "",
    val publishedDate : String = "",
    val furtherReadingLink : String = "",
    val source : String = "",
    val description : String = "",
    val content : String = "",
    val imgUrl : String = "",
    val country : String = "EN"
)


@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val prevKey : Int?,
    val nextKey : Int?
)
