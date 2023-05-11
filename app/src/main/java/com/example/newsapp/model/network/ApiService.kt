package com.example.newsapp.model.network

import com.example.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

class ApiConfig {
    companion object{

        private val authInterceptor = Interceptor{chain ->
            val req = chain.request()
            val requestHeader = req.newBuilder()
                .addHeader("x-api-key", BuildConfig.API_KEY)
                .build()
            chain.proceed(requestHeader)
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun getInstance() : NewsApiService{
            return retrofit.create(NewsApiService::class.java)
        }
    }
}

interface NewsApiService{
    @GET("search")
    suspend fun getNews(
        @QueryMap query : MutableMap<String, Any>
    ) : Response<SearchNewsResponse>
}