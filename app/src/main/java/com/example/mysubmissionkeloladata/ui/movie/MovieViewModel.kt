package com.example.mysubmissionkeloladata.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mysubmissionkeloladata.data.remote.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movie = MutableLiveData<String>()

    fun insertMovie(){
        _movie.value = "This is bait"
    }

    val movies = Transformations.switchMap(_movie) {
        movieRepository.getMovies()
    }
}