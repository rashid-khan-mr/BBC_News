package com.example.bbc_news.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.bbc_news.R
import com.example.bbc_news.databinding.ActivityNewsDetailBinding
import com.example.bbc_news.model.Article
import com.example.bbc_news.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

//import com.squareup.picasso.Callback
//import com.squareup.picasso.Picasso

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    lateinit var sharedViewModel: SharedViewModel
    lateinit var article: Article
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)


        retrieveArticleDetails()
        observeArticleDetailData()

    }

    /**
     *it'll observe the details of article selected by user
     */
    private fun observeArticleDetailData(){
        sharedViewModel.selectedArticle.observe(this, { article ->
            updateUI(article)
        })
    }
    /**
     *Retrieve the article from the intent
     */
    private fun retrieveArticleDetails(){
        try{
        val article = intent.getParcelableExtra<Article>("article")
        article?.let {
            sharedViewModel.setArticle(it)
        }
        }catch (e: Exception){
        e.printStackTrace()
        }
    }

    /**
     *update the UI on the basis of data observed from intent
     */
    private fun updateUI(article: Article?) {
        if (article != null) {
            Glide.with(applicationContext)
                .load(article.urlToImage)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Handle load failure
                        Log.e("Glide", "Image load failed: ${article.urlToImage}")
                        return false // Return false to allow Glide to display the error placeholder
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Handle load success
                        Log.d("Glide", "Image load successful: ${article.urlToImage}")
                        return false // Return false to allow Glide to display the loaded resource
                    }
                })
                .into(binding.detailsImg)
            /*  Picasso.get()
                .load(article.urlToImage)
                .error(R.drawable.phonelogix)
                .into(binding.detailsImg, object : Callback {
                    override fun onSuccess() {
                        Log.d("LIST_SI- imgUrl ","url loadedsucessful ")
                        // Image loaded successfully
                    }

                    override fun onError(e: Exception?) {
                        Log.d("LIST_SI- imgUrl ","url error  ")
                        // Handle error
                    }
                })*/
            binding.detailsTitle.text = article.title
            binding.detailsContent.text = article.content
        }
    }
}