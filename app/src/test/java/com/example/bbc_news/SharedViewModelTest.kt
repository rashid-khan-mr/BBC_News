package com.example.bbc_news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.bbc_news.model.Article
import com.example.bbc_news.viewmodel.SharedViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SharedViewModelTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var sharedViewModel: SharedViewModel

    @Mock
    private lateinit var observer: Observer<Article>

    @Before
    fun setup() {
        // Initialize the mocks
        MockitoAnnotations.initMocks(this)

        sharedViewModel = SharedViewModel()

        // Observe the LiveData
        viewModel.selectedArticle.observeForever(observer)
    }
    // Subject under test
    private lateinit var viewModel: SharedViewModel
      @Test
    fun setArticle_shouldUpdateLiveData() {
        // Mock data
        val expectedArticle = Article("auther","title","des","url","imageurl","publishedat","contnent")

        // Call the ViewModel method
        sharedViewModel.setArticle(expectedArticle)

        // Verify that the LiveData is updated
        assertEquals(expectedArticle, sharedViewModel.selectedArticle.value)
    }

    

}