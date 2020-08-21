package com.example.mysubmissionkeloladata.ui.favorite.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.remote.MovieRepository

class MovieFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movie = MutableLiveData<String>()

    fun insertMovie() {
        _movie.value = "This is bait"
    }

    val favoriteMovie = Transformations.switchMap(_movie) { movieRepository.getMovieFavoritePaged() }
}