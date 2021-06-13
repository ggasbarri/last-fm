package com.ggasbarri.lastfm.ui.album.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.ggasbarri.lastfm.R
import com.ggasbarri.lastfm.databinding.ItemSavedAlbumBinding
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import javax.inject.Inject

class SavedAlbumAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : PagingDataAdapter<AlbumWithTracks, SavedAlbumViewHolder>(
    SavedAlbumDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedAlbumViewHolder {
        return SavedAlbumViewHolder(
            ItemSavedAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), imageLoader = imageLoader
        )

    }

    override fun onBindViewHolder(holder: SavedAlbumViewHolder, position: Int) {
        holder.bind(albumWithTracks = getItem(position))
    }
}

class SavedAlbumViewHolder(
    private val binding: ItemSavedAlbumBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(albumWithTracks: AlbumWithTracks?) {
        albumWithTracks?.let {
            binding.model = albumWithTracks

            val imageRequest = ImageRequest.Builder(binding.root.context)
                .data(albumWithTracks.album.smallImageUrl)
                .target(binding.albumIv)
                .fallback(R.drawable.ic_question_mark)
                .transformations(
                    RoundedCornersTransformation(
                        binding.root.context.resources.getDimension(
                            R.dimen.album_item_image_cornerRadius
                        )
                    )
                )
                .build()

            imageLoader.enqueue(imageRequest)
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