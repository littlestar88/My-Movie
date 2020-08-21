package com.example.mysubmissionkeloladata.ui.favorite.tvshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.data.local.entity.DataTvShow
import com.example.mysubmissionkeloladata.data.remote.api.ApiRepository
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.items_list.view.*

class TvShowFavoritePagedAdapter(
    private val context: Context,
    private val onClickListener: (DataTvShow) -> Unit
) : PagedListAdapter<DataTvShow, TvShowFavoritePagedAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataTvShow>() {
            override fun areItemsTheSame(oldItem: DataTvShow, newItem: DataTvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataTvShow, newItem: DataTvShow): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.items_list, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItem(position) != null){
            val movie = getItem(position)
            movie?.let { holder.bindItem(it, onClickListener) }
        }
    }


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(
            data: DataTvShow,
            onClickListener: (DataTvShow) -> Unit
        ){
            itemView.tvTitle.text = data.name
            Picasso.get().load(ApiRepository.IMAGE_URL + data.posterPath)
                .placeholder(R.drawable.ic_refresh_black_24dp)
                .into(itemView.img_poster)
            itemView.tvDescription.text = data.overview
            itemView.tvRating.text = data.voteAverage.toString()
            containerView.setOnClickListener { onClickListener(data) }
        }
    }
}