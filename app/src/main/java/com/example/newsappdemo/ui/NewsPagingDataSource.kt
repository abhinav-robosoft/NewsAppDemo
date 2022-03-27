package com.example.newsappdemo.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.network.NewsApi
import com.example.newsappdemo.util.Constants.INITIAL_PAGE_INDEX

class NewsPagingDataSource(
    private val newsApi : NewsApi
    ) : PagingSource<Int, Article>()  {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = newsApi.getBreakingNews("in", position)
            val article = response.body()?.articles
            LoadResult.Page(
                data = article!!,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if(article.isEmpty()) null else position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}