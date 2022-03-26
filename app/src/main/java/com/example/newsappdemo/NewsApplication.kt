package com.example.newsappdemo

import android.app.Application
import com.example.newsappdemo.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(
                listOf(
                    apiModule,
                    viewModelModule,
                    repositoryModule,
                    retrofitModule,
                    articleDetailsDatabase
                )
            )
        }
    }
}