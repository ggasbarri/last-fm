package com.ggasbarri.lastfm.ui.album.detail

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import com.commit451.coiltransformations.ColorFilterTransformation
import com.ggasbarri.lastfm.R
import com.ggasbarri.lastfm.databinding.AlbumDetailFragmentBinding
import com.ggasbarri.lastfm.util.MemoryCacheKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumDetailFragment()
    }

    @Inject
    lateinit var adapter: AlbumTracksAdapter
    @Inject
    lateinit var imageLoader: ImageLoader

    lateinit var binding: AlbumDetailFragmentBinding

    private val viewModel: AlbumDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            AlbumDetailFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@AlbumDetailFragment.viewModel
            }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parameters by navArgs<AlbumDetailFragmentArgs>()
        viewModel.albumRemoteId = parameters.remoteId

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setupTracksRecyclerView()

        loadAlbumImage(parameters.memoryCacheKey)
    }


    private fun loadAlbumImage(memoryCacheKey: MemoryCacheKey) {

        val bitmap: Bitmap? = imageLoader.memoryCache[memoryCacheKey.memoryCacheKey]
        val imageRequest = ImageRequest.Builder(binding.root.context)
            .data(bitmap)
            .target(binding.albumDetailIv)
            .fallback(R.drawable.ic_question_mark)
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

    private fun setupTracksRecyclerView() {

        viewModel.tracks.observe(viewLifecycleOwner, { topAlbums ->
            lifecycleScope.launch { topAlbums?.data?.let { adapter.submitList(it) } }
        })

        binding.tracksRv.apply {
            adapter = this@AlbumDetailFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            // Add separators
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}