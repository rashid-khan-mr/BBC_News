package com.example.bbc_news

import com.example.bbc_news.model.NewsResponse
import com.example.bbc_news.repository.NewsApiService
import com.example.bbc_news.repository.NewsRepository
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NewsRepositoryTest {

    @Mock
    private lateinit var mockApiService: NewsApiService

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsRepository = NewsRepository(mockApiService)
    }

    @Test
    fun getTopHeadlines_success_shouldReturnNewsResponse() = runBlocking {
        // Mock data
        val source = "bbc-news"
        val expectedResponse = NewsResponse("",1, listOf())

        // Mock API service response
        `when`(mockApiService.getTopHeadlines(source)).thenReturn(expectedResponse)

        // Call the repository method
        val actualResponse = newsRepository.getTopHeadlines(source)

        // Verify that the response is not null
        assertNotNull(actualResponse)
        // You can add more assertions based on your specific response structure
    }

}