package com.example.mysubmissionkeloladata.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.vo.Resource

interface MovieDataSource {
    fun getMovies(): LiveData<Resource<List<DataMovie>>>
    fun getTvShows(): LiveData<Resource<List<DataTvShow>>>
    fun getMovieFavoritePaged(): LiveData<Resource<PagedList<DataMovie>>>
    fun getTvShowFavoritePaged(): LiveData<Resource<PagedList<DataTvShow>>>
    fun setMovieState(dataMovie: DataMovie, state: Boolean)
    fun setTvShowState(dataTvShow: DataTvShow, state: Boolean)
}