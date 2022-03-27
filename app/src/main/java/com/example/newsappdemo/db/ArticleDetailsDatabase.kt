package com.example.newsappdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappdemo.models.Article

@Database(
    entities = [Article::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class ArticleDetailsDatabase : RoomDatabase() {

    abstract val articleDetailsDao: ArticleDetailsDao

//    companion object {
//        @Volatile
//        private var instance: ArticleDetailsDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also { instance = it }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                ArticleDetailsDatabase::class.java,
//                "article_db.db"
//            ).build()
 //   }
}