package com.example.mysubmissionkeloladata.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.remote.MovieRepository
import com.example.mysubmissionkeloladata.utils.LiveDataTestUtil
import com.example.mysubmissionkeloladata.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieViewModelTest{
    private lateinit var movieViewModel: MovieViewModel
    private val mainRepository = Mockito.mock(MovieRepository::class.java)
    private val observer = Mockito.mock(Observer::class.java)

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieViewModel = MovieViewModel(mainRepository)
    }

    @Test
    fun getMovie() {
        val dataMovie = Resource.success(listOf<DataMovie>())
        val movie = MutableLiveData<Resource<List<DataMovie>>>()
        movie.value = dataMovie

        Mockito.`when`(mainRepository.getMovies()).thenReturn(movie)

        movieViewModel.insertMovie()
        movieViewModel.movies.observeForever(observer as Observer<Resource<List<DataMovie>>>)

        val result = LiveDataTestUtil.getValues(movieViewModel.movies)

        Mockito.verify(observer).onChanged(dataMovie)
        Mockito.verify(mainRepository).getMovies()
        assertNotNull(result.data)
    }
}