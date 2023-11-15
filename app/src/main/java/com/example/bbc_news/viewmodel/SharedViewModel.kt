package com.example.bbc_news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bbc_news.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel(){
    private val _selectedArticle = MutableLiveData<Article>()

    val selectedArticle: LiveData<Article>
        get() = _selectedArticle

    fun setArticle(article: Article) {
        _selectedArticle.value = article
    }


}