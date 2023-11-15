package com.example.bbc_news.repository

import com.example.bbc_news.model.Article

interface NewsItemClickListener {
    fun onItemClick(article: Article)
}