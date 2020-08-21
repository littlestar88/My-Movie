package com.example.mysubmissionkeloladata.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow

@Database(
    entities = [DataMovie::class, DataTvShow::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private const val databaseName = "movie_catalogue.db"
        private lateinit var INSTANCE: MovieDatabase

        fun getDatabase(context: Context): MovieDatabase {
            synchronized(MovieDatabase::class.java) {
                INSTANCE =
                    Room.databaseBuilder(context, MovieDatabase::class.java, databaseName).build()
            }
            return INSTANCE
        }
    }
}