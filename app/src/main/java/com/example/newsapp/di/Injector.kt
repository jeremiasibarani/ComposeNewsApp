package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.model.database.NewsDatabase
import com.example.newsapp.model.network.ApiConfig
import com.example.newsapp.model.network.NewsApiService
import com.example.newsapp.repository.NewsRepository

object Injector {
    fun provideNewsRepository(
        context : Context
    ) : NewsRepository{
        val newsApiService : NewsApiService = ApiConfig.getInstance()
        val database = NewsDatabase.getDatabase(context)
        return NewsRepository(newsApiService, database)
    }
}