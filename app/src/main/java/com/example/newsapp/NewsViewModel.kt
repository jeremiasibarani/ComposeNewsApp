package com.example.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.network.SearchNewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.common.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init{
        getNews()
    }

    private val _uiState : MutableStateFlow<UiState<SearchNewsResponse>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<SearchNewsResponse>> get() = _uiState

    fun getNews(keyword : String = "Apple") {
        viewModelScope.launch {
            newsRepository.getNews(keyword).collect { result ->
                _uiState.value = result
            }
        }
    }

}