package com.example.mysubmissionkeloladata.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmissionkeloladata.R
import com.example.mysubmissionkeloladata.data.local.entity.DataMovie
import com.example.mysubmissionkeloladata.data.remote.api.ApiRepository
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.items_list.view.*

class MovieAdapter(
    private val context: Context,
    private val data: MutableList<DataMovie>,
    private val onClickListener: (DataMovie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun setMovie(data: List<DataMovie>) {
        this.data.clear()
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.items_list, parent, false)
        )


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], onClickListener)
    }


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(
            data: DataMovie,
            onClickListener: (DataMovie) -> Unit
        ) {
            itemView.tvTitle.text = data.title
            Picasso.get().load(ApiRepository.IMAGE_URL + data.posterPath)
                .placeholder(R.drawable.ic_refresh_black_24dp)
                .into(itemView.img_poster)
            itemView.tvDescription.text = data.overview
            itemView.tvRating.text = data.voteAverage.toString()
            containerView.setOnClickListener { onClickListener(data) }
        }
    }
}