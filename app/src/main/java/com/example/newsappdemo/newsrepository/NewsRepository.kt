package  com.example.newsappdemo.newsrepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.newsappdemo.db.ArticleDetailsDatabase
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.network.NewsApi
import com.example.newsappdemo.ui.NewsPagingDataSource

class NewsRepository(
    private val newsApi: NewsApi,
    private val db: ArticleDetailsDatabase,
    private val newsPagingDataSource: NewsPagingDataSource
) {

    fun getBreakingNews() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { newsPagingDataSource }
        ).liveData

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsApi.searchResultForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.articleDetailsDao.upsert(article)

    fun getSavedNews() = db.articleDetailsDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = db.articleDetailsDao.deleteArticle(article)
}
