package com.example.newsappdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappdemo.models.Article

@Dao
interface ArticleDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}