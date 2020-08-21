package com.example.mysubmissionkeloladata.ui.detail

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.data.remote.api.ApiRepository
import com.example.mysubmissionkeloladata.ui.home.HomeActivity
import com.example.mysubmissionkeloladata.ui.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var dataMovie: DataMovie
    private lateinit var dataTvShow: DataTvShow
    private lateinit var menuItem: Menu
    private lateinit var type: String

    companion object {
        const val RESULT_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra(HomeActivity.DATA_EXTRA) && intent.hasExtra(HomeActivity.TYPE)) {
            detailViewModel = obtainViewModel(application)
            when (intent.getStringExtra(HomeActivity.TYPE)) {
                HomeActivity.MOVIE -> {
                    type = HomeActivity.MOVIE
                    dataMovie = intent.getParcelableExtra<DataMovie>(HomeActivity.DATA_EXTRA) as DataMovie
                    detailViewModel._detailMovie.value = dataMovie
                    detailViewModel._detailMovie.observe(this, observerDetailMovie)
                }
                HomeActivity.TVSHOW -> {
                    type = HomeActivity.TVSHOW
                    dataTvShow = intent.getParcelableExtra<DataTvShow>(HomeActivity.DATA_EXTRA) as DataTvShow
                    detailViewModel._detailTv.value = dataTvShow
                    detailViewModel._detailTv.observe(this, observerDetailTvShow)
                }
            }
        }
    }

    private fun initDetailMovie(data: DataMovie) {
        supportActionBar?.title = data.title

        Picasso.get().load(ApiRepository.IMAGE_URL + data.posterPath).into(iv_poster_detail)
        Picasso.get().load(ApiRepository.IMAGE_URL + data.backdropPath).into(iv_backdrop)
        tv_title_detail.text = data.title
        tv_popularity.text = data.popularity.toString()
        tv_overview_detail.text = data.overview
        tv_score.text= data.voteAverage.toString()
    }

    private fun initDetailTvShow(data: DataTvShow) {
        supportActionBar?.title = data.name
        Picasso.get().load(ApiRepository.IMAGE_URL + data.posterPath).into(iv_poster_detail)
        Picasso.get().load(ApiRepository.IMAGE_URL + data.backdropPath).into(iv_backdrop)
        tv_title_detail.text = data.name
        tv_popularity.text = data.popularity.toString()
        tv_overview_detail.text = data.overview
        tv_score.text = data.voteAverage.toString()
      }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        favoriteState()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.fav -> {
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        when (type) {
            HomeActivity.MOVIE -> {
                if (dataMovie.favorite) {
                    menuItem.getItem(0).icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_red_24)
                } else {
                    menuItem.getItem(0).icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
                }
            }
            HomeActivity.TVSHOW -> {
                if (dataTvShow.favorite) {
                    menuItem.getItem(0).icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_red_24)
                } else {
                    menuItem.getItem(0).icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }
    }

    private fun setFavorite() {
        when (type) {
            HomeActivity.MOVIE -> {
                if (dataMovie.favorite) {
                    detailViewModel.setMovieState(dataMovie, false)
                    menuItem.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_red_24)
                    toast(resources.getString(R.string.remove_favorite))
                } else {
                    detailViewModel.setMovieState(dataMovie, true)
                    menuItem.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
                    toast(resources.getString(R.string.add_favorite))
                }
            }
            HomeActivity.TVSHOW -> {
                if (dataTvShow.favorite) {
                    detailViewModel.setTvShowState(dataTvShow, false)
                    menuItem.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_red_24)
                    toast(resources.getString(R.string.remove_favorite))
                } else {
                    detailViewModel.setTvShowState(dataTvShow, true)
                    menuItem.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
                    toast(resources.getString(R.string.add_favorite))
                }
            }
        }
    }

    private fun obtainViewModel(application: Application): DetailViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private val observerDetailMovie = Observer<DataMovie> {
        if (it != null) {
            initDetailMovie(it)
            pb_detail.visibility = View.GONE
        }
    }

    private val observerDetailTvShow = Observer<DataTvShow> {
        if (it != null) {
            initDetailTvShow(it)
            pb_detail.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_CODE)
        finish()
    }
}