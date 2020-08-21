package com.example.mysubmissionkeloladata.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mysubmissionkeloladata.data.local.map.TvShowMap
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TvShowMap.tableName)
data class DataTvShow(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = TvShowMap.id)
    var id: String,

    @NonNull
    @ColumnInfo(name = TvShowMap.name)
    var name: String,

    @NonNull
    @ColumnInfo(name = TvShowMap.overview)
    var overview: String,

    @NonNull
    @ColumnInfo(name = TvShowMap.posterPath)
    var posterPath: String,

    @NonNull
    @ColumnInfo(name = TvShowMap.favorite)
    var favorite: Boolean = false,

    @NonNull
    @ColumnInfo(name = TvShowMap.voteAverage)
    var voteAverage: Double,

    @NonNull
    @ColumnInfo(name = TvShowMap.popularity)
    var popularity: Double,

    @NonNull
    @ColumnInfo(name = TvShowMap.backdropPath)
    var backdropPath: String
) : Parcelable