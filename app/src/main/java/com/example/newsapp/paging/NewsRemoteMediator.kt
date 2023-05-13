package com.example.newsapp.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsapp.model.database.NewsDatabase
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.model.database.RemoteKey
import com.example.newsapp.model.network.NewsApiService
import com.example.newsapp.util.Constants.PAGE_REQUEST
import com.example.newsapp.util.Constants.PAGE_SIZE_REQUEST

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val database : NewsDatabase,
    private val newsApiService: NewsApiService,
    private val query : MutableMap<String, Any>
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        try{
            val page = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
            }
            query[PAGE_REQUEST] = page
            query[PAGE_SIZE_REQUEST] = state.config.pageSize
            val response = newsApiService.getNews(query)
            val responseBody = response.body()
            Log.i("REMOTEMEDIATOR-TAG", "${response.raw()}")
            val articles = responseBody?.articles
            val endOfPaginationReached = articles.isNullOrEmpty()

            val localNews = articles?.map { news ->
                NewsEntity(
                    title = news.title,
                    author = news.author,
                    publishedDate = news.publishedDate,
                    furtherReadingLink = news.furtherReadingLink,
                    source = news.source.name,
                    description = news.description,
                    content = news.content,
                    imgUrl = news.imgUrl
                )
            } ?: emptyList()

            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    database.remoteKeysDao().deleteAllRemoteKeys()
                    database.newsDao().deleteAllNews()
                }

                val prevKey = if(page == INITIAL_PAGE_INDEX) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1
                val keys = localNews.map{
                    RemoteKey(
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                database.remoteKeysDao().insertKeys(keys)
                database.newsDao().insertAllNews(localNews)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e : Exception){
            return MediatorResult.Error(e)
        }


    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NewsEntity>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysById(data.id)
        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, NewsEntity>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysById(data.id)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, NewsEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysById(id)
            }
        }
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

}