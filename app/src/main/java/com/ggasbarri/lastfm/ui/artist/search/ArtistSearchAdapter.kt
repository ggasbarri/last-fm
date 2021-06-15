package com.ggasbarri.lastfm.ui.artist.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.ggasbarri.lastfm.R
import com.ggasbarri.lastfm.databinding.ItemArtistSearchResultBinding
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.util.ItemClickListener
import javax.inject.Inject

class ArtistSearchAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : PagingDataAdapter<Artist, SavedArtistViewHolder>(ArtistSearchResultDiffCallback()) {

    var onItemClickListener: ItemClickListener<ItemArtistSearchResultBinding, Artist>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArtistViewHolder {
        return SavedArtistViewHolder(
            ItemArtistSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            imageLoader = imageLoader
        ).also {
            it.itemView.setOnClickListener { _ ->
                val item = getItem(it.bindingAdapterPosition)
                if (item != null)
                    onItemClickListener?.onItemClick(it.binding, item, it.bindingAdapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: SavedArtistViewHolder, position: Int) {
        holder.bind(artist = getItem(position))
    }
}

class SavedArtistViewHolder(
    val binding: ItemArtistSearchResultBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(artist: Artist?) {
        artist?.let {
            binding.model = artist

            val imageRequest = ImageRequest.Builder(binding.root.context)
                .data(
                    if (artist.smallImageUrl.isNullOrBlank()) R.drawable.ic_question_mark
                    else artist.smallImageUrl
                )
                .target(binding.artistIv)
                .fallback(R.drawable.ic_question_mark)
                .transformations(
                    RoundedCornersTransformation(
                        binding.root.context.resources.getDimension(
                            R.dimen.album_item_image_cornerRadius
                        )
                    )
                )
                .listener { _, metadata ->
                    binding.imageCacheKey = metadata.memoryCacheKey
                }
                .build()

            imageLoader.enqueue(imageRequest)
        }
    }

}

class ArtistSearchResultDiffCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.remoteId == newItem.remoteId
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}