package com.example.mysubmissionkeloladata.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysubmissionkeloladata.data.remote.MovieRepository
import com.example.mysubmissionkeloladata.di.Injection
import com.example.mysubmissionkeloladata.ui.detail.DetailViewModel
import com.example.mysubmissionkeloladata.ui.favorite.movie.MovieFavoriteViewModel
import com.example.mysubmissionkeloladata.ui.favorite.tvshow.TvShowFavoriteViewModel
import com.example.mysubmissionkeloladata.ui.movie.MovieViewModel
import com.example.mysubmissionkeloladata.ui.tvshow.TvShowViewModel

class ViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        private lateinit var instance: ViewModelFactory
        fun getInstance(application: Application): ViewModelFactory {
            synchronized(ViewModelFactory::class.java) {
                instance = ViewModelFactory(Injection.provideRepository(application))
            }
            return instance
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            MovieViewModel::class.java -> {
                return MovieViewModel(movieRepository) as T
            }
            TvShowViewModel::class.java -> {
                return TvShowViewModel(movieRepository) as T
            }
            DetailViewModel::class.java -> {
                return DetailViewModel(movieRepository) as T
            }
            MovieFavoriteViewModel::class.java -> {
                return MovieFavoriteViewModel(movieRepository) as T
            }
            TvShowFavoriteViewModel::class.java -> {
                return TvShowFavoriteViewModel(movieRepository) as T
            }
        }

        throw IllegalArgumentException("Unknown ViewModel class : " + modelClass.name)
    }
}