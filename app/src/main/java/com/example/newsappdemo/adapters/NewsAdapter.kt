package com.example.newsappdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappdemo.R
import com.example.newsappdemo.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.ArticleViewHolder>(differCallback) {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.itemView.apply {
            Glide.with(this).load(article?.urlToImage).into(ivArticleImage)
            tvSource.text = article?.source?.name
            tvTitle.text = article?.title
            tvDescription.text = article?.description
            tvPublishedAt.text = article?.publishedAt

            setOnClickListener {
                onItemClickListener?.let {
                    if (article != null) {
                        it(article)
                    }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}













