package com.ggasbarri.lastfm.ui.search

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
import javax.inject.Inject

class ArtistSearchAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : PagingDataAdapter<Artist, SavedArtistViewHolder>(ArtistSearchResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArtistViewHolder {
        return SavedArtistViewHolder(
            ItemArtistSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            imageLoader = imageLoader
        )

    }

    override fun onBindViewHolder(holder: SavedArtistViewHolder, position: Int) {
        holder.bind(artist = getItem(position))
    }
}

class SavedArtistViewHolder(
    private val binding: ItemArtistSearchResultBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(artist: Artist?) {
        artist?.let {
            binding.model = artist

            val imageRequest = ImageRequest.Builder(binding.root.context)
                .data(artist.smallImageUrl)
                .target(binding.artistIv)
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

class ArtistSearchResultDiffCallback : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.remoteId == newItem.remoteId
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}