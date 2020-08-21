package com.example.mysubmissionkeloladata.data.remote.model

data class MovieModel (
    val id: String,
    val title: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val vote_average: Double,
    val popularity: Double,
    val backdrop_path: String? = ""
)