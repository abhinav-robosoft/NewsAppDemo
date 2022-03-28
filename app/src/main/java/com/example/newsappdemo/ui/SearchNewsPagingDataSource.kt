package com.example.newsappdemo.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.network.NewsApi

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class SearchNewsPagingDataSource(
    private val newsApi: NewsApi,
    private val query: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = newsApi.searchResultForNews(query)
            val article = response.body()?.articles

            LoadResult.Page(
                data = article!!,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (article.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}