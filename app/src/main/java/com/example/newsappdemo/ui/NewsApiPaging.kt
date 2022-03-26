package com.example.newsappdemo.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappdemo.models.Article
import com.example.newsappdemo.newsrepository.NewsRepository

class NewsApiPaging(private val newsRepository: NewsRepository) : PagingSource<Int, Article>()  {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state?.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1

        return try {

            val data = newsRepository.getBreakingNews("us", page)

            LoadResult.Page(
                data = data.body()?.articles!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.body()?.articles?.isEmpty()!!) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}