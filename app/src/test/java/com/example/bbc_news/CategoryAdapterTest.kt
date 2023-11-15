package com.example.bbc_news

import android.content.Context
import com.example.bbc_news.model.Article
import com.example.bbc_news.repository.NewsItemClickListener
import com.example.bbc_news.ui.CategoryAdapter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CategoryAdapterTest {
    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockListener: NewsItemClickListener

    private lateinit var categoryAdapter: CategoryAdapter


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        categoryAdapter = CategoryAdapter(mockContext, mockListener)
    }

    @Test
    fun getItemCount_emptyList_shouldReturnZero() {
        categoryAdapter.setAppList(emptyList())
        assert(categoryAdapter.itemCount == 0)
    }

    @Test
    fun getItemCount_nonEmptyList_shouldReturnCorrectSize() {
        val articles = listOf(
            Article("bbc-news","headlines"," df dfdfd ","httpsdfdf","this is image url",
                "published at 12 nov","details here"),
            Article("bbc-news","headlines"," df dfdfd ","httpsdfdf","this is image url",
                "published at 11 nov","details here"),

            )
        categoryAdapter.setAppList(articles)
        assert(categoryAdapter.itemCount == articles.size)
    }

}