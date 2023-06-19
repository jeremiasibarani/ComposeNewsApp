package com.example.newsapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.BuildConfig
import com.example.newsapp.model.database.BookmarkNewsEntity
import com.example.newsapp.model.database.NewsDatabase
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.model.network.NewsApiService
import com.example.newsapp.paging.NewsRemoteMediator
import com.example.newsapp.util.Constants.API_KEY_REQUEST
import com.example.newsapp.util.Constants.DEFAULT_PAGE_SIZE_REQUEST
import com.example.newsapp.util.Constants.KEYWORD_REQUEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class NewsRepository(
    private val newsApiService : NewsApiService,
    private val newsDatabase: NewsDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getNews(
        keyword : String,
        pageSize : Int = DEFAULT_PAGE_SIZE_REQUEST
    ) : Flow<PagingData<NewsEntity>> {
            val query = mutableMapOf<String, Any>(
                KEYWORD_REQUEST to keyword,
                API_KEY_REQUEST to BuildConfig.API_KEY
            )
            return Pager(
                config = PagingConfig(
                    pageSize = pageSize
                ),
                remoteMediator = NewsRemoteMediator(
                    database = newsDatabase,
                    newsApiService = newsApiService,
                    query = query
                ),
                pagingSourceFactory = {
                    newsDatabase.newsDao().getAllNews()
                }
            ).flow

    }

    suspend fun bookmarkNews(news : NewsEntity){
        val bookmarkedNews = BookmarkNewsEntity(
            id = news.id,
            title = news.title,
            author = news.author,
            publishedDate = news.publishedDate,
            furtherReadingLink = news.furtherReadingLink,
            source = news.source,
            description = news.description,
            content = news.content,
            imgUrl = news.imgUrl,
            country = news.country
        )
        newsDatabase.bookmarkNewsDao().insertBookmarkedNews(bookmarkedNews)
    }

    fun getAllBookmarkedNews() = newsDatabase.bookmarkNewsDao().getAllBookmarkedNews()
    suspend fun deleteFromBookmark(newsTitle : String) = newsDatabase.bookmarkNewsDao().deleteBookmarkedNews(newsTitle)
    fun getDetailNewsById(id : Long) = newsDatabase.newsDao().getDetailNewsById(id).filterNotNull()
    fun searchBookmarkedNews(newsTitle : String) = newsDatabase.bookmarkNewsDao().searchBookmarkedNews(newsTitle)

    fun checkIfNewsExistsInBookmark(newsTitle : String) = newsDatabase.bookmarkNewsDao().checkIfNewsExists(newsTitle)
}