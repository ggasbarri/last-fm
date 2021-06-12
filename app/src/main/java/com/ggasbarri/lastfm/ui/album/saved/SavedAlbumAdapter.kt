package com.ggasbarri.lastfm.ui.album.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ggasbarri.lastfm.databinding.ItemSavedAlbumBinding
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import javax.inject.Inject

class SavedAlbumAdapter @Inject constructor() :
    PagingDataAdapter<AlbumWithTracks, SavedAlbumViewHolder>(
        SavedAlbumDiffCallback()
    ) {

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

    fun bind(album: AlbumWithTracks?) {
        album?.let {
            binding.model = album
        }
    }

}

class SavedAlbumDiffCallback : DiffUtil.ItemCallback<AlbumWithTracks>() {
    override fun areItemsTheSame(oldItem: AlbumWithTracks, newItem: AlbumWithTracks): Boolean {
        return oldItem.album.id == newItem.album.id
    }

    override fun areContentsTheSame(oldItem: AlbumWithTracks, newItem: AlbumWithTracks): Boolean {
        return oldItem.album == newItem.album
                && oldItem.tracks.size == newItem.tracks.size
    }
}