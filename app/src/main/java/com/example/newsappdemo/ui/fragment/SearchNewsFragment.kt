package com.example.newsappdemo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappdemo.R
import com.example.newsappdemo.adapters.NewsAdapter
import com.example.newsappdemo.adapters.NewsAdapterForDb
import com.example.newsappdemo.ui.NewsViewModel
import com.example.newsappdemo.util.Constants.SEARCH_NEWS_TIME_DELAY
import com.example.newsappdemo.util.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

private const val  TAG = "SearchNewsFragment"
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private val viewModel: NewsViewModel = get()
    lateinit var newsAdapterForDb: NewsAdapterForDb

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        newsAdapterForDb.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                       viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

//        viewModel.searchNews?.observeForever {
//            it?.toString()?.let { it1 -> Log.d("Abhinav", it1) } ?: kotlin.run {
//                Log.d("Abhinav", "empty") }
//
//            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
//        }
//
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapterForDb.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapterForDb = NewsAdapterForDb()
        rvSearchNews.apply {
            adapter = newsAdapterForDb
            layoutManager = LinearLayoutManager(activity)
        }
    }
}