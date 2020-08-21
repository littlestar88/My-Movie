package com.example.mysubmissionkeloladata.di

import android.app.Application
import com.example.mysubmissionkeloladata.data.local.LocalDataSource
import com.example.mysubmissionkeloladata.data.remote.MovieRepository
import com.example.mysubmissionkeloladata.data.remote.RemoteDataSource
import com.example.mysubmissionkeloladata.utils.AppExecutors

object Injection {
    fun provideRepository(application: Application): MovieRepository {
        val remoteDataSource = RemoteDataSource()
        val localDataSource = LocalDataSource(application)
        val appExecutors = AppExecutors()
        return MovieRepository(remoteDataSource, localDataSource, appExecutors)
    }
}