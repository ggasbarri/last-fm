package com.ggasbarri.lastfm.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ggasbarri.lastfm.databinding.ItemSavedAlbumBinding
import com.ggasbarri.lastfm.db.models.Album
import javax.inject.Inject

class SavedAlbumAdapter @Inject constructor() : PagingDataAdapter<Album, SavedAlbumViewHolder>(SavedAlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedAlbumViewHolder {
        return SavedAlbumViewHolder(
            ItemSavedAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: SavedAlbumViewHolder, position: Int) {
        holder.bind(album = getItem(position))
    }
}

class SavedAlbumViewHolder(private val binding: ItemSavedAlbumBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album?) {
            album?.let {
                binding.model = album
            }
        }

}

class SavedAlbumDiffCallback() : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}