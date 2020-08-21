package com.example.mysubmissionkeloladata.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mysubmissionkeloladata.data.MovieDataSource
import com.example.mysubmissionkeloladata.data.NetworkBoundResource
import com.example.mysubmissionkeloladata.data.local.LocalDataSource
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.data.remote.model.MovieModel
import com.example.mysubmissionkeloladata.data.remote.model.TvShowModel
import com.example.mysubmissionkeloladata.data.remote.response.ApiResponse
import com.example.mysubmissionkeloladata.utils.AppExecutors
import com.example.mysubmissionkeloladata.vo.Resource

open class MovieRepository (private val remoteDataSource: RemoteDataSource,
private val localDataSource: LocalDataSource,
private val appExecutors: AppExecutors
) : MovieDataSource {
    private val pageSize = 5

    override fun getMovies(): LiveData<Resource<List<DataMovie>>> {
        return object : NetworkBoundResource<List<DataMovie>, List<MovieModel>>(appExecutors) {
            override fun loadDatabase(): LiveData<List<DataMovie>> {
                return localDataSource.getMovie()
            }

            override fun shouldFetch(data: List<DataMovie>): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieModel>>> {
                return remoteDataSource.getMovie()
            }

            override fun saveCallResult(data: List<MovieModel>) {
                val movie = mutableListOf<DataMovie>()
                for (i in data.indices) {
                    movie.add(
                        DataMovie(
                            data[i].id,
                            data[i].title.toString(),
                            data[i].overview.toString(),
                            data[i].poster_path.toString(),
                            false,
                            data[i].vote_average,
                            data[i].popularity,
                            data[i].backdrop_path.toString()
                        )
                    )
                }
                localDataSource.insertMovie(movie)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<List<DataTvShow>>> {
        return object : NetworkBoundResource<List<DataTvShow>, List<TvShowModel>>(appExecutors) {
            override fun saveCallResult(data: List<TvShowModel>) {
                val tvShow = mutableListOf<DataTvShow>()
                for (i in data.indices) {
                    tvShow.add(
                        DataTvShow(
                            data[i].id,
                            data[i].name.toString(),
                            data[i].overview.toString(),
                            data[i].poster_path.toString(),
                            false,
                            data[i].vote_average,
                            data[i].popularity,
                            data[i].backdrop_path.toString()
                        )
                    )
                }
                localDataSource.insertTvShow(tvShow)
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowModel>>> {
                return remoteDataSource.getTvShow()
            }

            override fun shouldFetch(data: List<DataTvShow>): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadDatabase(): LiveData<List<DataTvShow>> {
                return localDataSource.getTvShow()
            }

        }.asLiveData()
    }

    override fun getMovieFavoritePaged(): LiveData<Resource<PagedList<DataMovie>>> {
        return object :
            NetworkBoundResource<PagedList<DataMovie>, List<MovieModel>>(appExecutors) {
            override fun saveCallResult(data: List<MovieModel>) {

            }

            override fun createCall(): LiveData<ApiResponse<List<MovieModel>>> {
                return MutableLiveData<ApiResponse<List<MovieModel>>>()
            }

            override fun shouldFetch(data: PagedList<DataMovie>): Boolean {
                return false
            }

            override fun loadDatabase(): LiveData<PagedList<DataMovie>> {
                return LivePagedListBuilder(
                    localDataSource.getFavoriteMoviePaged(),
                    pageSize
                ).build()
            }

        }.asLiveData()
    }

    override fun getTvShowFavoritePaged(): LiveData<Resource<PagedList<DataTvShow>>> {
        return object :
            NetworkBoundResource<PagedList<DataTvShow>, List<TvShowModel>>(appExecutors) {
            override fun saveCallResult(data: List<TvShowModel>) {

            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowModel>>> {
                return MutableLiveData<ApiResponse<List<TvShowModel>>>()
            }

            override fun shouldFetch(data: PagedList<DataTvShow>): Boolean {
                return false
            }

            override fun loadDatabase(): LiveData<PagedList<DataTvShow>> {
                return LivePagedListBuilder(
                    localDataSource.getFavoriteTvShowPaged(),
                    pageSize
                ).build()
            }

        }.asLiveData()
    }

    override fun setMovieState(dataMovie: DataMovie, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setMovieState(dataMovie, state)
        }
    }

    override fun setTvShowState(dataTvShow: DataTvShow, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setTvShowState(dataTvShow, state)
        }
    }


}