package com.example.mysubmissionkeloladata.data.remote.api

import com.example.mysubmissionkeloladata.data.remote.response.movie.MovieResponse
import com.example.mysubmissionkeloladata.data.remote.response.tvshow.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun loadMovies(
        @Query("api_key") api_key: String
    ): Call<MovieResponse>

    @GET("discover/tv")
    fun loadTvShows(
        @Query("api_key") api_key: String
    ): Call<TvShowResponse>

}