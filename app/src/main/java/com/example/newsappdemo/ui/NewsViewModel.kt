package com.example.newsappdemo.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.models.NewsResponse
import com.example.newsappdemo.newsrepository.NewsRepository
import com.example.newsappdemo.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews = newsRepository.getBreakingNews().cachedIn(viewModelScope)
    // var searchNews : MutableLiveData<PagingData<Article>>? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

//    fun searchNews(searchQuery: String){
//        searchNews = newsRepository.searchNews(searchQuery)?.cachedIn(viewModelScope) as MutableLiveData<PagingData<Article>>?
//    }

//    fun setSearchKey(key : String){
//        newsRepository.setSearchKey(key)
//        searchNews = newsRepository.searchNews()?.cachedIn(viewModelScope)
//    }

    //    fun getSearchResult(searchKey : String) {
//        searchNews = newsRepository.searchNews(searchKey) as MutableLiveData<PagingData<Article>>?
//    }
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, 1)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}












