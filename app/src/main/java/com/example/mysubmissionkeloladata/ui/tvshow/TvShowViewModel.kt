package com.example.mysubmissionkeloladata.ui.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mysubmissionkeloladata.data.remote.MovieRepository

class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _tvShow = MutableLiveData<String>()

    fun insertTvShow() {
        _tvShow.value = "This is bait"
    }

    val tvShows = Transformations.switchMap(_tvShow) {
        movieRepository.getTvShows() }

}