package com.ggasbarri.lastfm.ui.album.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.ggasbarri.lastfm.R
import com.ggasbarri.lastfm.databinding.ItemSavedAlbumBinding
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import com.ggasbarri.lastfm.util.ItemClickListener
import com.ggasbarri.lastfm.util.MemoryCacheKey
import javax.inject.Inject

class SavedAlbumAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : PagingDataAdapter<AlbumWithTracks, SavedAlbumViewHolder>(
    SavedAlbumDiffCallback()
) {

    var onItemClickListener: ItemClickListener<ItemSavedAlbumBinding, AlbumWithTracks>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedAlbumViewHolder {
        return SavedAlbumViewHolder(
            ItemSavedAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), imageLoader = imageLoader
        ).also {
            it.itemView.setOnClickListener { _ ->
                val item = getItem(it.bindingAdapterPosition)
                if (item != null)
                    onItemClickListener?.onItemClick(it.binding, item, it.bindingAdapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: SavedAlbumViewHolder, position: Int) {
        holder.bind(albumWithTracks = getItem(position))
    }
}

class SavedAlbumViewHolder(
    val binding: ItemSavedAlbumBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(albumWithTracks: AlbumWithTracks?) {
        albumWithTracks?.let {
            binding.model = albumWithTracks

            val imageRequest = ImageRequest.Builder(binding.root.context)
                .data(
                    if (albumWithTracks.album.smallImageUrl.isNullOrBlank()) R.drawable.ic_question_mark
                    else albumWithTracks.album.smallImageUrl
                )
                .target(binding.albumIv)
                .fallback(R.drawable.ic_question_mark)
                .transformations(
                    RoundedCornersTransformation(
                        binding.root.context.resources.getDimension(
                            R.dimen.album_item_image_cornerRadius
                        )
                    )
                )
                .listener { _, metadata ->
                    binding.memoryCacheKey =
                        MemoryCacheKey(metadata.memoryCacheKey ?: MemoryCache.Key(""))
                }
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