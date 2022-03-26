package com.example.newsappdemo.util

import android.app.Application
import androidx.room.Room
import com.example.newsappdemo.BuildConfig
import com.example.newsappdemo.db.ArticleDetailsDao
import com.example.newsappdemo.db.ArticleDetailsDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Scope.retrofitBuilder(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(get()))
        .client(get())
        .build()
}

fun Scope.retrofitHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        cache(get())
        retryOnConnectionFailure(true)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
    }.build()
}

fun Scope.getDataBase(application : Application) : ArticleDetailsDatabase {
     return Room.databaseBuilder(application, ArticleDetailsDatabase::class.java, "news_db")
         .fallbackToDestructiveMigration()
         .build()
}

fun Scope.getDao(database: ArticleDetailsDatabase) : ArticleDetailsDao {
    return database.articleDetailsDao
}
