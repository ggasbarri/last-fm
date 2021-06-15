package com.ggasbarri.lastfm.ui.album.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ggasbarri.lastfm.databinding.ItemTrackBinding
import com.ggasbarri.lastfm.db.models.Track
import com.ggasbarri.lastfm.util.ItemClickListener
import javax.inject.Inject

class AlbumTracksAdapter @Inject constructor() :
    ListAdapter<Track, TrackViewHolder>(TrackDiffCallback()) {

    var onItemClickListener: ItemClickListener<ItemTrackBinding, Track>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            ItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also {
            it.itemView.setOnClickListener { _ ->
                val item = getItem(it.bindingAdapterPosition)
                if (item != null)
                    onItemClickListener?.onItemClick(it.binding, item, it.bindingAdapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track = getItem(position))
    }
}

class TrackViewHolder(
    val binding: ItemTrackBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track?) {
        track?.let { binding.model = track }
    }

}

class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.rank == newItem.rank
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}