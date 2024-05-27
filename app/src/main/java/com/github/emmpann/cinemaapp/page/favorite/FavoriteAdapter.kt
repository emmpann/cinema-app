package com.github.emmpann.cinemaapp.page.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.emmpann.cinemaapp.core.remote.response.DetailResponse
import com.github.emmpann.cinemaapp.databinding.ItemMovieBinding

class FavoriteAdapter : ListAdapter<DetailResponse, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailResponse>() {
            override fun areItemsTheSame(oldItem: DetailResponse, newItem: DetailResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DetailResponse, newItem: DetailResponse): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailResponse) {
            binding.titleTextView.text = item.title
            binding.rating.text = String.format("%.1f", item.voteAverage)
            Glide
                .with(binding.root)
                .load("https://image.tmdb.org/t/p/w185${item.posterPath}")
                .into(binding.posterView)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailResponse)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(item) }
    }
}