package  com.example.newsappdemo.newsrepository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.newsappdemo.db.ArticleDetailsDatabase
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.network.NewsApi
import com.example.newsappdemo.ui.NewsPagingDataSource
import com.example.newsappdemo.ui.fragment.DataSourceSearch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsRepository(
    private val newsApi: NewsApi,
    private val db: ArticleDetailsDatabase,
    private val newsPagingDataSource: NewsPagingDataSource
) {

    private var searchKey: String? = null
    private var dataSourceSearch: DataSourceSearch? = null
//        get() {
//            if ((field == null || field?.invalid == true) && searchKey != null) {
//                field = DataSourceSearch(newsApi, searchKey!!)
//            }
//            return field
//        }

//   fun setSearchKey(key : String){
//       searchKey = key
//   }

    fun getBreakingNews() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {newsPagingDataSource}
        ).liveData

//    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
//        newsApi.searchResultForNews(searchQuery, pageNumber)

//    fun searchNews(searchQuery: String) =
//       if (searchNewsPagingDataSource == null || searchNewsPagingDataSource?.invalid == true) {
//            searchNewsPagingDataSource?.invalidate()
//           searchNewsPagingDataSource = DataSourceSearch(newsApi, searchQuery)
//
////           GlobalScope.launch {
////              val response = newsApi.searchResultForNews("india", 1)
////               Log.d("Abhinav", response.toString())
////           }
//
//           Log.d("Abhinav", "em")
//            Pager(
//                config = PagingConfig(
//                    pageSize = 20,
//                    maxSize = 100,
//                    enablePlaceholders = false
//                ),
//                pagingSourceFactory = { searchNewsPagingDataSource!!}
//            ).liveData
//        }else{
//            null
//       }

//    fun searchNews(searchKey : String?) =
//        if(searchKey != null) {
//            dataSourceSearch?.invalidate()
//            dataSourceSearch = null
//            dataSourceSearch = DataSourceSearch(newsApi, searchKey)
//            this.searchKey = searchKey
//            Pager(
//                config = PagingConfig(
//                    pageSize = 20,
//                    maxSize = 100,
//                    enablePlaceholders = true
//                ),
//                pagingSourceFactory = {dataSourceSearch!! }
//            ).liveData
//        }else{
//            null
//        }

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsApi.searchResultForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.articleDetailsDao.upsert(article)

    fun getSavedNews() = db.articleDetailsDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = db.articleDetailsDao.deleteArticle(article)
}
