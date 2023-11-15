package com.example.bbc_news.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
    )
