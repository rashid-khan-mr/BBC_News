package com.example.bbc_news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.bbc_news.model.Article
import com.example.bbc_news.model.NewsResponse
import com.example.bbc_news.repository.NewsApiService
import com.example.bbc_news.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
public class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _newsList = MutableLiveData<List<Article>>()
    val newsLiveData: LiveData<List<Article>> get() = _newsList
    private val sourceNameMutable = MutableLiveData<String>()
    val sourceName: LiveData<String> get() = sourceNameMutable

    fun getTopHeadlines(selectedSource : String) {
        viewModelScope.launch {
            try {
                Log.v("getTopHeadlines-ViewModel", "start calling repository from viewmodel")

                if (_newsList.value==null || !selectedSource.equals(sourceNameMutable)) {
                    val response = withContext(Dispatchers.IO) {
                        repository.getTopHeadlines(selectedSource)
                    }
                    val articles = response.articles.sortedByDescending { it.publishedAt }
                    _newsList.value = articles
                    // Load and cache images

                    sourceNameMutable.value = selectedSource
//                    val articles = response.articles
//                    _newsList.value = articles
                    Log.v("getTdlines-ViewModel", "size: ${articles.size}")
                }

            } catch (e: Exception) {
                // Handle the exception
                Log.v("getTopHeadlines-ViewModel", "exception"+e.message)
            }
        }

    }
}
