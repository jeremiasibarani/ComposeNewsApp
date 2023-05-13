package com.example.newsapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "news")
//data class NewsEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id : Long = 0L,
//    val title : String = "",
//    val author : String = "",
//    val publishedDate : String = "",
//    val furtherReadingLink : String = "",
//    val source : String = "",
//    val excerpt : String = "",
//    val summary : String = "",
//    val topic : String = "",
//    val language : String = "",
//    val imgUrl : String = "",
//    val country : String = "",
//    val isBookmarked : Boolean = false
//)

// Todo(the problem with only marking the bookmarked news as true or false is everytime the app is open and the newest news fetched, the database will get resets and
//      the bookmark status will get resets as well, we should separate the database of bookmark news and the one for pagination, simply add it to the bookmark database when
//      bookmark icon clicked)

@Entity(tableName = "bookmarked_news")
data class BookmarkNewsEntity(
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
