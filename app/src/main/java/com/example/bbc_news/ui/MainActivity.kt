package com.example.bbc_news.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbc_news.R
import com.example.bbc_news.databinding.ActivityMainBinding
import com.example.bbc_news.model.Article
import com.example.bbc_news.repository.NewsItemClickListener
import com.example.bbc_news.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(),
    NewsItemClickListener {
    private lateinit var binding: ActivityMainBinding
        lateinit var viewModel: NewsViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    var article: List<Article> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        setRecyclerView(article)
        observeData()
        sourceSelectionBySpinner()
    }

    /**
     *this method will set recyclerview to the adapter
     */
    private fun setRecyclerView(dataList: List<Article>) {
        try {
            categoryAdapter = CategoryAdapter(this, this)
            val categoryLinearLayoutManager = LinearLayoutManager(this)
            categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager = categoryLinearLayoutManager
            categoryAdapter.setAppList(dataList)
            binding.recyclerView.adapter = categoryAdapter
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    /**
     *NewsDetailActivity will call on any news clicked from news list
     */
    override fun onItemClick(article: Article) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra("article",article)
        startActivity(intent)
    }


    /**
     *this method will observe data(news list)
     */
    private fun observeData(){
        viewModel.newsLiveData.observe(this, Observer { articles ->
            categoryAdapter.setAppList(articles)
        })

        viewModel.sourceName.observe(this, Observer { source ->
            binding.sourceName.text = source
        })
    }

    /**
     *it's a listener for spinner, it'll fetch top headlines based on source selection by user
     */
    private fun sourceSelectionBySpinner(){
        try {
            binding.newsSourceSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedSource =
                            resources.getStringArray(R.array.source_array)[position]
                        viewModel.getTopHeadlines(selectedSource)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //nothing selected
                    }
                }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    }

