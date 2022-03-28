package com.example.newsappdemo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.newsrepository.NewsRepository
import com.example.newsappdemo.util.Constants
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews = newsRepository.getBreakingNews().cachedIn(viewModelScope)

    private val currentQuery = MutableLiveData(Constants.DEFAULT_SEARCH_QUARY)

    val searchNews = currentQuery.switchMap { queryString ->
        newsRepository.searchNews(queryString).cachedIn(viewModelScope)
    }

    fun searchQuery(query: String) {
        currentQuery.postValue(query)
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}












