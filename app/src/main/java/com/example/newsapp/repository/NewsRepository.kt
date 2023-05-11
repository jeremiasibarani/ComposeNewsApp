package com.example.newsapp.repository

import com.example.newsapp.model.network.NewsApiService
import com.example.newsapp.model.network.SearchNewsResponse
import com.example.newsapp.ui.common.UiState
import com.example.newsapp.util.Constants.DEFAULT_PAGE_SIZE_REQUEST
import com.example.newsapp.util.Constants.KEYWORD_REQUEST
import com.example.newsapp.util.Constants.PAGE_SIZE_REQUEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(
    private val apiService : NewsApiService
) {
    fun getNews(
        keyword : String,
        pageSize : Int = DEFAULT_PAGE_SIZE_REQUEST
    ) : Flow<UiState<SearchNewsResponse>> = flow {
        try{
            val query = mutableMapOf<String,Any>()
            query[KEYWORD_REQUEST] = keyword
            query[PAGE_SIZE_REQUEST] = pageSize
            val response = apiService.getNews(query)
            val responseBody = response.body()
            if(response.isSuccessful){
                responseBody?.let{data ->
                    emit(UiState.Success(data))
                }
            }else{
                emit(UiState.Error("${response.code()} : ${response.message()}"))
            }
        }catch (e : Exception){
            emit(UiState.Error(e.message.toString()))
        }
    }
}