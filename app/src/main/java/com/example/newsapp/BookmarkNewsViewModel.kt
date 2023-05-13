package com.example.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.database.BookmarkNewsEntity
import com.example.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch

class BookmarkNewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getAllBookmarkedNews() = newsRepository.getAllBookmarkedNews()
    fun deleteFromBookmark(bookmarkedNews : BookmarkNewsEntity) = viewModelScope.launch {
        newsRepository.deleteFromBookmark(bookmarkedNews)
    }

}