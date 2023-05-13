package com.example.newsapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NewsEntity::class, RemoteKey::class, BookmarkNewsEntity::class],
    exportSchema = false,
    version = 1
)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun bookmarkNewsDao() : BookmarkNewsDao
    abstract fun newsDao() : NewsDao
    abstract fun remoteKeysDao() : RemoteKeysDao

    companion object{
        @Volatile
        private var INSTANCE : NewsDatabase? = null

        @JvmStatic
        fun getDatabase(context : Context) : NewsDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

}