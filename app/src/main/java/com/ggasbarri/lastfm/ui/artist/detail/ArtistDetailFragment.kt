package com.ggasbarri.lastfm.ui.artist.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.commit451.coiltransformations.ColorFilterTransformation
import com.ggasbarri.lastfm.R
import com.ggasbarri.lastfm.databinding.ArtistDetailFragmentBinding
import com.ggasbarri.lastfm.databinding.ItemArtistSearchResultBinding
import com.ggasbarri.lastfm.databinding.ItemTopAlbumBinding
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.ui.artist.search.ArtistSearchFragmentDirections
import com.ggasbarri.lastfm.util.ItemClickListener
import com.ggasbarri.lastfm.util.MemoryCacheKey
import com.ggasbarri.lastfm.util.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArtistDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistDetailFragment()
    }

    private val viewModel: ArtistDetailViewModel by viewModels()
    private lateinit var binding: ArtistDetailFragmentBinding

    @Inject
    lateinit var adapter: TopAlbumsAdapter

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            ArtistDetailFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@ArtistDetailFragment.viewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parameters by navArgs<ArtistDetailFragmentArgs>()
        viewModel.artist = parameters.artist

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setupTopAlbumsRecyclerView()

        loadArtistImage(parameters)

        // Setup Empty text & ProgressBar
        handleLoadingSate()
    }

    private fun loadArtistImage(parameters: ArtistDetailFragmentArgs) {
        viewModel.artist?.let { artist ->
            val imageRequest = ImageRequest.Builder(binding.root.context)
                .data(artist.largeImageUrl)
                .target(binding.artistDetailIv)
                .fallback(R.drawable.ic_question_mark)
                .placeholderMemoryCacheKey(parameters.imageCacheKey.memoryCacheKey)
                .transformations(
                    ColorFilterTransformation(
                        color = ResourcesCompat.getColor(
                            resources, R.color.image_color_filter, requireContext().theme
                        )
                    )
                )
                .build()

            imageLoader.enqueue(imageRequest)
        }
    }

    private fun setupTopAlbumsRecyclerView() {

        viewModel.topAlbums.observe(viewLifecycleOwner, { topAlbums ->
            lifecycleScope.launch { topAlbums?.let { adapter.submitData(it) } }
        })

        binding.topAlbumsRv.apply {
            this@ArtistDetailFragment.adapter.onItemClickListener =
                object : ItemClickListener<ItemTopAlbumBinding, Album> {
                    override fun onItemClick(
                        binding: ItemTopAlbumBinding,
                        item: Album,
                        position: Int
                    ) {
                        if (item.remoteId == null) {
                            this@ArtistDetailFragment.binding.rootLayout.snackBar(
                                R.string.artist_detail_album_not_in_server_error
                            )
                        } else {
                            findNavController().navigate(
                                directions = ArtistDetailFragmentDirections.actionArtistDetailFragmentToAlbumDetailFragment(
                                    item.remoteId,
                                    binding.memoryCacheKey ?: MemoryCacheKey(
                                        MemoryCache.Key("")
                                    )
                                )
                            )
                        }
                    }
                }

            adapter = this@ArtistDetailFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        // Add separators
        binding.topAlbumsRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun handleLoadingSate() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadingState ->
                binding.topAlbumsProgressBar.isVisible = loadingState.refresh is LoadState.Loading
                binding.topAlbumsEmptyTv.isVisible =
                    adapter.itemCount < 1 && loadingState.refresh !is LoadState.Loading
            }
        }
    }
}