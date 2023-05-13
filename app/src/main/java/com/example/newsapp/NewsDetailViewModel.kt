package com.example.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getDetailNewsById(id : Long) = newsRepository.getDetailNewsById(id)

    fun bookmarkNews(news : NewsEntity) = viewModelScope.launch {
        newsRepository.bookmarkNews(news)
    }

}