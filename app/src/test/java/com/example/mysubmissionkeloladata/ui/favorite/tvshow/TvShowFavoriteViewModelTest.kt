package com.example.mysubmissionkeloladata.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
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

class TvShowFavoriteViewModelTest{

    private lateinit var tvShowFavoriteViewModel: TvShowFavoriteViewModel
    private val movieRepository = Mockito.mock(MovieRepository::class.java)
    private val observer = Mockito.mock(Observer::class.java)

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        tvShowFavoriteViewModel = TvShowFavoriteViewModel(movieRepository)
    }

    @Test
    fun getFavoriteTvShow() {
        val dataTvShowFavorite = MutableLiveData<Resource<PagedList<DataTvShow>>>()
        val tvShow = Mockito.mock(PagedList::class.java) as PagedList<DataTvShow>
        dataTvShowFavorite.value = Resource.success(tvShow)

        Mockito.`when`(movieRepository.getTvShowFavoritePaged()).thenReturn(dataTvShowFavorite)

        tvShowFavoriteViewModel.insertTvShow()
        tvShowFavoriteViewModel.favoriteTvShow.observeForever(observer as Observer<Resource<PagedList<DataTvShow>>>)

        val result = LiveDataTestUtil.getValues(tvShowFavoriteViewModel.favoriteTvShow)

        Mockito.verify(observer).onChanged(Resource.success(tvShow))
        Mockito.verify(movieRepository).getTvShowFavoritePaged()
        assertNotNull(result.data)
    }
}