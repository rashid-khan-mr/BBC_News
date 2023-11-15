package com.example.bbc_news.repository

import com.example.bbc_news.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

//    @GET("https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=5280c49611654fe09c70d9d3944d2940")
//    suspend fun getTopHeadlines(): NewsResponse

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String = "5280c49611654fe09c70d9d3944d2940"
    ): NewsResponse
}