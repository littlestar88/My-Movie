package com.example.mysubmissionkeloladata.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.data.remote.MovieRepository
import com.example.mysubmissionkeloladata.utils.LiveDataTestUtil
import com.example.mysubmissionkeloladata.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TvShowViewModelTest{

    private lateinit var tvShowViewModel: TvShowViewModel
    private val mainRepository = Mockito.mock(MovieRepository::class.java)
    private val observer = Mockito.mock(Observer::class.java)

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        tvShowViewModel = TvShowViewModel(mainRepository)
    }

    @Test
    fun getTvShow() {
        val dataTvShow = Resource.success(listOf<DataTvShow>())
        val tvShow = MutableLiveData<Resource<List<DataTvShow>>>()
        tvShow.value = dataTvShow

        Mockito.`when`(mainRepository.getTvShows()).thenReturn(tvShow)

        tvShowViewModel.insertTvShow()
        tvShowViewModel.tvShows.observeForever(observer as Observer<Resource<List<DataTvShow>>>)

        val result = LiveDataTestUtil.getValues(tvShowViewModel.tvShows)

        Mockito.verify(observer).onChanged(dataTvShow)
        Mockito.verify(mainRepository).getTvShows()
        assertNotNull(result.data)
    }
}