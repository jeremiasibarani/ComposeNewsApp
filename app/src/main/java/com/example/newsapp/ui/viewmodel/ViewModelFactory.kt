package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepository

class ViewModelFactory private constructor(
    private val newsRepository: NewsRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(newsRepository) as T
        }else if(modelClass.isAssignableFrom(BookmarkNewsViewModel::class.java)){
            return BookmarkNewsViewModel(newsRepository) as T
        }else if(modelClass.isAssignableFrom(NewsDetailViewModel::class.java)){
            return NewsDetailViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{

        private var INSTANCE : ViewModelFactory? = null

        fun getInstance(
            newsRepository : NewsRepository
        ) : ViewModelFactory {
            if(INSTANCE == null){
                INSTANCE = ViewModelFactory(newsRepository)
            }
            return INSTANCE!!
        }
    }

}