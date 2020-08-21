package com.example.mysubmissionkeloladata.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.data.remote.MovieRepository

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var _detailMovie = MutableLiveData<DataMovie>()
    var _detailTv = MutableLiveData<DataTvShow>()

    fun setMovieState(dataMovie: DataMovie, state: Boolean){
        movieRepository.setMovieState(dataMovie, state)
    }

    fun setTvShowState(dataTvShow: DataTvShow, state: Boolean){
        movieRepository.setTvShowState(dataTvShow, state)
    }
}