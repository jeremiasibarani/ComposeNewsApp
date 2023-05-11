package com.example.newsapp

import android.content.Context
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
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{

        private var INSTANCE : ViewModelFactory? = null

        fun getInstance(
            newsRepository : NewsRepository
        ) : ViewModelFactory{
            if(INSTANCE == null){
                INSTANCE = ViewModelFactory(newsRepository)
            }
            return INSTANCE!!
        }
    }

}