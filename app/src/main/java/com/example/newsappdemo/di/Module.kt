package com.example.newsappdemo.di

import android.app.Application
import androidx.room.Room
import com.example.newsappdemo.db.ArticleDetailsDao
import com.example.newsappdemo.db.ArticleDetailsDatabase
import com.example.newsappdemo.network.NewsApi
import com.example.newsappdemo.newsrepository.NewsRepository
import com.example.newsappdemo.ui.NewsViewModel
import com.example.newsappdemo.util.getDao
import com.example.newsappdemo.util.getDataBase
import com.example.newsappdemo.util.retrofitBuilder
import com.example.newsappdemo.util.retrofitHttpClient
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelModule: Module = module {

    viewModel { NewsViewModel(get()) }

}

val apiModule: Module = module {
    single(createdAtStart = false) { get<Retrofit>().create(NewsApi::class.java) }
}

val repositoryModule = module {
    single { NewsRepository(get(), get()) }
}

val retrofitModule = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }
    single { GsonBuilder().create() }
    single { retrofitHttpClient() }
    single { retrofitBuilder() }
    single {
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
                header("Accept", "application/json")
            }.build())
        }
    }
}

val articleDetailsDatabase = module {
    single {getDataBase(androidApplication()) }
    single {getDao(get()) }
}