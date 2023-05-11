package com.example.newsapp.di

import com.example.newsapp.model.network.ApiConfig
import com.example.newsapp.model.network.NewsApiService
import com.example.newsapp.repository.NewsRepository

object Injector {
    fun provideNewsRepository() : NewsRepository{
        val newsApiService : NewsApiService = ApiConfig.getInstance()
        return NewsRepository(newsApiService)
    }
}