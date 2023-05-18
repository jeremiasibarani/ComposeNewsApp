package com.example.newsapp.model.database

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkNewsDao{
    @Insert
    suspend fun insertBookmarkedNews(bookmarkNews : BookmarkNewsEntity)

    @Query("SELECT * FROM bookmarked_news")
    fun getAllBookmarkedNews() : Flow<List<BookmarkNewsEntity>>

    @Query("SELECT * FROM bookmarked_news WHERE title LIKE '%' || :newsTitle || '%'")
    fun searchBookmarkedNews(newsTitle : String) : Flow<List<BookmarkNewsEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_news WHERE title= :newsTitle)")
    fun checkIfNewsExists(newsTitle: String) : Flow<Boolean>

    @Query("DELETE FROM bookmarked_news WHERE title =:newsTitle")
    suspend fun deleteBookmarkedNews(newsTitle: String)
}

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNews(listOfNews : List<NewsEntity>)

    @Query("SELECT * FROM news")
    fun getAllNews() : PagingSource<Int, NewsEntity>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getDetailNewsById(id : Long) : Flow<NewsEntity>

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()
}

@Dao
interface RemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKeys(remoteKeys : List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysById(id : Long) : RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}
