package com.example.mysubmissionkeloladata.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.ui.detail.DetailActivity
import com.example.mysubmissionkeloladata.ui.home.HomeActivity
import com.example.mysubmissionkeloladata.ui.viewmodel.ViewModelFactory
import com.example.mysubmissionkeloladata.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.support.v4.toast

class MovieFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel
    private var data = mutableListOf<DataMovie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(HomeActivity.INSTANCE, MovieFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            movieViewModel = obtainViewModel(requireActivity())
            movieViewModel.insertMovie()
            movieViewModel.movies.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    when (it.status) {
                        Status.LOADING -> {
                            pb_movie.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            pb_movie.visibility = View.GONE
                            it.data?.let { it1 -> movieAdapter.setMovie(it1) }
                            Log.d("Movie", it.toString())
                            movieAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            pb_movie.visibility = View.GONE
                            toast("Error")
                        }
                    }
                }
            })

            movieAdapter = MovieAdapter(requireContext(), data) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(HomeActivity.DATA_EXTRA, it)
                intent.putExtra(HomeActivity.TYPE, HomeActivity.MOVIE)
                startActivity(intent)
            }

            rv_movie.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    private fun obtainViewModel(fragmentActivity: FragmentActivity): MovieViewModel {
        val factory = ViewModelFactory.getInstance(fragmentActivity.application)
        return ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]
    }
}