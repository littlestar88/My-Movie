package com.example.mysubmissionkeloladata.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.remote.MovieRepository
import com.example.mysubmissionkeloladata.ui.movie.MovieViewModel
import com.example.mysubmissionkeloladata.utils.LiveDataTestUtil
import com.example.mysubmissionkeloladata.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieFavoriteViewModelTest {

    private lateinit var movieFavoriteViewModel: MovieFavoriteViewModel
    private val movieRepository = Mockito.mock(MovieRepository::class.java)
    private val observer = Mockito.mock(Observer::class.java)

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieFavoriteViewModel = MovieFavoriteViewModel(movieRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val dataMovieFavorite = MutableLiveData<Resource<PagedList<DataMovie>>>()
        val movieFavorite = Mockito.mock(PagedList::class.java) as PagedList<DataMovie>
        dataMovieFavorite.value = Resource.success(movieFavorite)

        Mockito.`when`(movieRepository.getMovieFavoritePaged()).thenReturn(dataMovieFavorite)

        movieFavoriteViewModel.insertMovie()
        movieFavoriteViewModel.favoriteMovie.observeForever(observer as Observer<Resource<PagedList<DataMovie>>>)

        val result = LiveDataTestUtil.getValues(movieFavoriteViewModel.favoriteMovie)

        Mockito.verify(observer).onChanged(Resource.success(movieFavorite))
        Mockito.verify(movieRepository).getMovieFavoritePaged()
        assertNotNull(result.data)
    }
}