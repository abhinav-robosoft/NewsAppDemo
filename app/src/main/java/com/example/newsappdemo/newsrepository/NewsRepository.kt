package  com.example.newsappdemo.newsrepository

import com.example.newsappdemo.db.ArticleDetailsDatabase
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.network.NewsApi

class NewsRepository(
    private val newsApi: NewsApi,
    private val db: ArticleDetailsDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsApi.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsApi.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.articleDetailsDao.upsert(article)

    fun getSavedNews() = db.articleDetailsDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = db.articleDetailsDao.deleteArticle(article)
}
