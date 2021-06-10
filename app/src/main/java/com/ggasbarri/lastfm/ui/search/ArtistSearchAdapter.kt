package com.ggasbarri.lastfm.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ggasbarri.lastfm.databinding.ItemArtistSearchResultBinding
import com.ggasbarri.lastfm.db.models.Artist
import javax.inject.Inject

class ArtistSearchAdapter @Inject constructor() : PagingDataAdapter<Artist, SavedArtistViewHolder>(ArtistSearchResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArtistViewHolder {
        return SavedArtistViewHolder(
            ItemArtistSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: SavedArtistViewHolder, position: Int) {
        holder.bind(Artist = getItem(position))
    }
}

class SavedArtistViewHolder(private val binding: ItemArtistSearchResultBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(Artist: Artist?) {
        Artist?.let {
            binding.model = Artist
        }
    }

}

class ArtistSearchResultDiffCallback() : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}