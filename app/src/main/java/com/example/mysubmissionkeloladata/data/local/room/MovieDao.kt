package com.example.mysubmissionkeloladata.data.local.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.data.local.map.MovieMap
import com.example.mysubmissionkeloladata.data.local.map.TvShowMap

@Dao
interface MovieDao {
    @WorkerThread
    @Query("SELECT * FROM ${MovieMap.tableName}")
    fun getMovie(): LiveData<List<DataMovie>>

    @WorkerThread
    @Query("SELECT * FROM ${MovieMap.tableName} WHERE ${MovieMap.favorite} = 1")
    fun getFavoriteMovieAsPaged(): DataSource.Factory<Int, DataMovie>

    @WorkerThread
    @Query("SELECT * FROM ${TvShowMap.tableName}")
    fun getTvShow(): LiveData<List<DataTvShow>>

    @WorkerThread
    @Query("SELECT * FROM ${TvShowMap.tableName} WHERE ${TvShowMap.favorite} = 1")
    fun getFavoriteTvShowAsPaged(): DataSource.Factory<Int, DataTvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(data: List<DataMovie>)

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateMovie(data: DataMovie): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(data: List<DataTvShow>)

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateTvShow(data: DataTvShow): Int
}