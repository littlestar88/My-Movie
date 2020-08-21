package com.example.mysubmissionkeloladata.ui.favorite.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mysubmissionkeloladata.data.remote.MovieRepository

class TvShowFavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _tvShow = MutableLiveData<String>()

    fun insertTvShow() {
        _tvShow.value = "This is bait"
    }

    val favoriteTvShow = Transformations.switchMap(_tvShow) { movieRepository.getTvShowFavoritePaged() }
}