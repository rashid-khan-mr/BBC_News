package com.example.bbc_news.repository

import android.util.Log
import com.example.bbc_news.model.NewsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NewsRepository  @Inject constructor(private val apiService: NewsApiService) {
    suspend fun getTopHeadlines(source: String): NewsResponse {
        return apiService.getTopHeadlines(source)
    }

    /*suspend fun getTopHeadlines(source: String): NewsResponse {
        //Log.v("getopHeadlines-NewsRepository","called"+apiService.getTopHeadlines())
        //return apiService.getTopHeadlines()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)
        return newsApiService.getTopHeadlines(source)
    }*/
}