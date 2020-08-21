package com.example.mysubmissionkeloladata.ui.favorite.movie

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.remote.api.ApiRepository
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.items_list.view.*

class MovieFavoritePagedAdapter(
    private val context: Context,
    private val onClickListener: (DataMovie) -> Unit
) : PagedListAdapter<DataMovie, MovieFavoritePagedAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataMovie>() {
            override fun areItemsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(
            data: DataMovie,
            onClickListener: (DataMovie) -> Unit
        ){
            itemView.tvTitle.text = data.title
            Picasso.get().load(ApiRepository.IMAGE_URL + data.posterPath)
                .placeholder(R.drawable.ic_refresh_black_24dp)
                .into(itemView.img_poster)
            itemView.tvDescription.text = data.overview
            itemView.tvRating.text = data.voteAverage.toString()
            containerView.setOnClickListener { onClickListener(data) }
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
}