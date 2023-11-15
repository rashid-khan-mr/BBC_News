package com.example.bbc_news.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.bbc_news.R
import com.example.bbc_news.databinding.NewsitemBinding
import com.example.bbc_news.model.Article
import com.example.bbc_news.repository.NewsItemClickListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CategoryAdapter (private var context: Context, private var listener: NewsItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mCategoryList: List<Article> = mutableListOf<Article>()

    @SuppressLint("NotifyDataSetChanged")
    fun setAppList(categoryModel: List<Article>) {
        mCategoryList= categoryModel ?: emptyList()
        if (mCategoryList.size>0){
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val appInfo = mCategoryList[position]
        (holder as CategoryAdapter.RecyclerHolderCatIcon).bind(appInfo, listener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = NewsitemBinding.inflate(layoutInflater, parent, false)
        return RecyclerHolderCatIcon(applicationBinding)
    }

    inner class RecyclerHolderCatIcon(private var applicationBinding: NewsitemBinding) : RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind(appInfo: Article, listener: NewsItemClickListener?) {
            applicationBinding.article  = appInfo

            applicationBinding.itemLayout.setOnClickListener(View.OnClickListener {
                listener?.onItemClick(appInfo)
            })
            loadImageUrl(applicationBinding.newsImg, appInfo.urlToImage)
        }
    }
    fun loadImageUrl(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context)
                .load(url)
                .skipMemoryCache(true)
                .into(view);
        }else {
            view.setImageResource(R.drawable.ic_launcher_background) // You can set a placeholder image here
        }
    }
}