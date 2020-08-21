package com.example.mysubmissionkeloladata.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.ui.favorite.FavoriteFragment
import com.example.mysubmissionkeloladata.ui.movie.MovieFragment
import com.example.mysubmissionkeloladata.ui.tvshow.TvShowFragment
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {

    companion object {
        const val DATA_EXTRA = "data"
        const val MOVIE = "movie"
        const val TVSHOW = "tv"
        const val TYPE = "type"
        const val INSTANCE = "instance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movie -> {
                    loadMovieFragment()
                }
                R.id.nav_tv -> {
                    loadTvShowFragment()
                }
                R.id.nav_fav -> {
                    loadFavoriteFragment()
                }
            }
            true
        }

        if (savedInstanceState == null) {
            nav_view.selectedItemId = R.id.nav_movie
        } else {
            when (savedInstanceState.getString(INSTANCE)) {
                MovieFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.nav_movie
                }
                TvShowFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.nav_tv
                }
                FavoriteFragment::class.java.simpleName -> {
                    nav_view.selectedItemId = R.id.nav_fav
                }
            }
        }

    }

    private fun loadFavoriteFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fl_content, FavoriteFragment(), FavoriteFragment::class.java.simpleName
            ).commit()
        supportActionBar?.setTitle(R.string.menu_favorite)
    }

    private fun loadMovieFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, MovieFragment(), MovieFragment::class.java.simpleName)
            .commit()
        supportActionBar?.setTitle(R.string.menu_movies)
    }

    private fun loadTvShowFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, TvShowFragment(), TvShowFragment::class.java.simpleName)
            .commit()
        supportActionBar?.setTitle(R.string.menu_tv_show)
    }
    
}