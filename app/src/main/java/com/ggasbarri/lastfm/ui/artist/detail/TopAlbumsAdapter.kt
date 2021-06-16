package com.ggasbarri.lastfm.ui.artist.detail

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
import com.ggasbarri.lastfm.databinding.ItemTopAlbumBinding
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.util.ItemClickListener
import com.ggasbarri.lastfm.image.MemoryCacheKey
import javax.inject.Inject

class TopAlbumsAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : PagingDataAdapter<Album, TopAlbumViewHolder>(TopAlbumDiffCallback()) {

    var onItemClickListener: ItemClickListener<ItemTopAlbumBinding, Album>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAlbumViewHolder {
        return TopAlbumViewHolder(
            ItemTopAlbumBinding.inflate(
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

    override fun onBindViewHolder(holder: TopAlbumViewHolder, position: Int) {
        holder.bind(album = getItem(position))
    }
}

class TopAlbumViewHolder(
    val binding: ItemTopAlbumBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album?) {
        album?.let {
            binding.model = album

            val imageRequest = ImageRequest.Builder(binding.root.context)
                .data(
                    if (album.imageUrl.isNullOrBlank()) R.drawable.ic_question_mark
                    else album.imageUrl
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
                    binding.memoryCacheKey =
                        MemoryCacheKey(metadata.memoryCacheKey ?: MemoryCache.Key(""))
                }
                .build()


            imageLoader.enqueue(imageRequest)
        }
    }

}

class TopAlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.remoteId == newItem.remoteId
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}