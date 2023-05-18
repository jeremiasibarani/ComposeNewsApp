package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.model.database.NewsEntity
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _requestNews = MutableStateFlow<PagingData<NewsEntity>>(PagingData.empty())
    val requestNews : StateFlow<PagingData<NewsEntity>> get() = _requestNews

    init{
        getNews()
    }

    fun getNews(keyword : String = "Apple") = viewModelScope.launch {
        newsRepository.getNews(keyword)
            .cachedIn(viewModelScope)
            .collect{ result ->
                _requestNews.value = result
            }
    }


}