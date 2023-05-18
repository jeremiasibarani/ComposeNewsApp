package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.database.BookmarkNewsEntity
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkNewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _bookmarkedNews = MutableStateFlow<List<BookmarkNewsEntity>>(emptyList())
    val bookmarkedNews : StateFlow<List<BookmarkNewsEntity>> get() = _bookmarkedNews

    init{
        getAllBookmarkedNews()
    }

    fun getAllBookmarkedNews() = viewModelScope.launch {
        newsRepository.getAllBookmarkedNews().collect{ result ->
            _bookmarkedNews.value = result
        }
    }
    fun deleteFromBookmark(newsTitle : String) = viewModelScope.launch {
        newsRepository.deleteFromBookmark(newsTitle)
    }
    fun searchBookmarkedNews(newsTitle : String) = viewModelScope.launch {
        newsRepository.searchBookmarkedNews(newsTitle).collect{ result ->
            _bookmarkedNews.value = result
        }
    }

}