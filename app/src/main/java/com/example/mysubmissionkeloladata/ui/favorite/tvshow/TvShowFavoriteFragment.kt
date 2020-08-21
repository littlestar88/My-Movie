package com.example.mysubmissionkeloladata.ui.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.ui.detail.DetailActivity
import com.example.mysubmissionkeloladata.ui.detail.DetailActivity.Companion.RESULT_CODE
import com.example.mysubmissionkeloladata.ui.home.HomeActivity
import com.example.mysubmissionkeloladata.ui.viewmodel.ViewModelFactory
import com.example.mysubmissionkeloladata.vo.Status
import kotlinx.android.synthetic.main.fragment_tv_show_favorite.*
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast


class TvShowFavoriteFragment : Fragment() {


    private lateinit var tvShowFavoritePagedAdapter: TvShowFavoritePagedAdapter
    private lateinit var tvShowFavoriteViewModel: TvShowFavoriteViewModel

    companion object {
        const val REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity!= null){
            tvShowFavoriteViewModel = obtainViewModel(activity!!)

            getFavoriteTvShow()

            tvShowFavoritePagedAdapter = TvShowFavoritePagedAdapter(requireContext()) {
                startActivityForResult<DetailActivity>(
                    REQUEST_CODE, HomeActivity.TYPE to HomeActivity.TVSHOW,
                    HomeActivity.DATA_EXTRA to it
                )
            }

            rv_fav_tv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = tvShowFavoritePagedAdapter
            }
        }
    }

    private fun obtainViewModel(fragmentActivity: FragmentActivity): TvShowFavoriteViewModel {
        val factory = ViewModelFactory.getInstance(fragmentActivity.application)
        return ViewModelProvider(requireActivity(), factory)[TvShowFavoriteViewModel::class.java]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CODE){
                getFavoriteTvShow()
            }
        }
    }

    private fun getFavoriteTvShow() {
        tvShowFavoriteViewModel.insertTvShow()
        tvShowFavoriteViewModel.favoriteTvShow.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    Status.SUCCESS -> {
                        pb_fav_tv.visibility = View.GONE
                        if (it.data!!.isNotEmpty()) {
                            tvShowFavoritePagedAdapter.submitList(it.data)
                            tv_fav_tv_no_data.visibility = View.GONE
                            rv_fav_tv.visibility = View.VISIBLE
                        } else {
                            tv_fav_tv_no_data.visibility = View.VISIBLE
                            rv_fav_tv.visibility = View.GONE
                        }
                        tvShowFavoritePagedAdapter.notifyDataSetChanged()
                    }
                    Status.LOADING -> {
                        pb_fav_tv.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        pb_fav_tv.visibility = View.GONE
                        toast("Error")
                    }
                }
            }
        })
    }


}