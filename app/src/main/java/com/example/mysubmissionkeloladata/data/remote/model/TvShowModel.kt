package com.example.mysubmissionkeloladata.data.remote.model

data class TvShowModel (
    val id: String,
    val name: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val vote_average: Double,
    val popularity: Double,
    val backdrop_path: String? = ""
)