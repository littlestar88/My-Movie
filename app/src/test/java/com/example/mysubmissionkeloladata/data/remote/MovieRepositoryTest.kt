package com.example.mysubmissionkeloladata.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.example.mysubmissionkeloladata.data.local.LocalDataSource
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.utils.AppExecutors
import com.example.mysubmissionkeloladata.utils.PagedListUtil
import com.example.mysubmissionkeloladata.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieRepositoryTest  {
    private val remoteDataSource = Mockito.mock(RemoteDataSource::class.java)
    private val localDataSource = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val movieRepository = MovieRepository(remoteDataSource, localDataSource, appExecutors)

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getMovieFavoritePaged() {
        val movie = listOf<DataMovie>()
        val dataSource = Mockito.mock(DataSource.Factory::class.java)

        Mockito.`when`(localDataSource.getFavoriteMoviePaged()).thenReturn(dataSource as DataSource.Factory<Int, DataMovie>)
        movieRepository.getMovieFavoritePaged()

        val result = Resource.success(PagedListUtil.mockPagedList(movie))

        Mockito.verify(localDataSource).getFavoriteMoviePaged()
        assertNotNull(result.data)
    }

    @Test
    fun getTvShowFavoritePaged() {
        val tvShow = listOf<DataTvShow>()
        val dataSource = Mockito.mock(DataSource.Factory::class.java)

        Mockito.`when`(localDataSource.getFavoriteTvShowPaged()).thenReturn(dataSource as DataSource.Factory<Int, DataTvShow>)
        movieRepository.getTvShowFavoritePaged()

        val result = Resource.success(PagedListUtil.mockPagedList(tvShow))

        Mockito.verify(localDataSource).getFavoriteTvShowPaged()
        assertNotNull(result.data)
    }
}