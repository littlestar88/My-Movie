package com.example.mysubmissionkeloladata.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.ui.detail.DetailActivity
import com.example.mysubmissionkeloladata.ui.home.HomeActivity
import com.example.mysubmissionkeloladata.ui.viewmodel.ViewModelFactory
import com.example.mysubmissionkeloladata.vo.Status
import kotlinx.android.synthetic.main.fragment_tv_show.*
import org.jetbrains.anko.support.v4.toast

class TvShowFragment : Fragment() {


    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowViewModel: TvShowViewModel
    private var data = mutableListOf<DataTvShow>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(HomeActivity.INSTANCE, TvShowFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            tvShowViewModel = obtainViewModel(activity!!)
            tvShowViewModel.insertTvShow()
            tvShowViewModel.tvShows.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    when(it.status){
                        Status.LOADING -> {
                            pb_tv.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            pb_tv.visibility = View.GONE
                            it.data?.let { it1 -> tvShowAdapter.setTvShow(it1) }
                            tvShowAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            pb_tv.visibility = View.GONE
                            toast("Error")
                        }
                    }
                }
            })

            tvShowAdapter = TvShowAdapter(requireContext(), data) {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(HomeActivity.DATA_EXTRA, it)
                intent.putExtra(HomeActivity.TYPE, HomeActivity.TVSHOW)
                startActivity(intent)
            }

            rv_tv.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }

    private fun obtainViewModel(fragmentActivity: FragmentActivity): TvShowViewModel {
        val factory = ViewModelFactory.getInstance(fragmentActivity.application)

        return ViewModelProvider(requireActivity(), factory)[TvShowViewModel::class.java]
    }
}