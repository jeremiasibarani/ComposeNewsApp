package com.example.newsapp

import androidx.lifecycle.ViewModel
import com.example.newsapp.repository.NewsRepository

class BookmarkNewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getAllBookmarkedNews() = newsRepository.getAllBookmarkedNews()

}