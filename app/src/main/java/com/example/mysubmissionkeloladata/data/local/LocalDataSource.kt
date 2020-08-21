package com.example.mysubmissionkeloladata.data.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.data.local.room.MovieDao
import com.example.mysubmissionkeloladata.data.local.room.MovieDatabase
import java.util.concurrent.Executors

open class LocalDataSource (context: Context) {

    private val movieDao: MovieDao = MovieDatabase.getDatabase(context).movieDao()

    private var executorService = Executors.newSingleThreadExecutor()

    open fun getMovie(): LiveData<List<DataMovie>> {
        return movieDao.getMovie()
    }

    open fun getFavoriteMoviePaged(): DataSource.Factory<Int, DataMovie> {
        return movieDao.getFavoriteMovieAsPaged()
    }

    open fun getTvShow(): LiveData<List<DataTvShow>> {
        return movieDao.getTvShow()
    }

    open fun getFavoriteTvShowPaged(): DataSource.Factory<Int, DataTvShow> {
        return movieDao.getFavoriteTvShowAsPaged()
    }

    fun insertMovie(data: List<DataMovie>){
        executorService.execute { movieDao.insertMovie(data) }
    }

    fun setMovieState(dataMovie: DataMovie, state: Boolean){
        dataMovie.favorite = state
        movieDao.updateMovie(dataMovie)
    }

    fun insertTvShow(data: List<DataTvShow>){
        executorService.execute { movieDao.insertTvShow(data) }
    }

    fun setTvShowState(dataTvShow: DataTvShow, state: Boolean){
        dataTvShow.favorite = state
        movieDao.updateTvShow(dataTvShow)
    }

}