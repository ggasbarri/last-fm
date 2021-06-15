package com.ggasbarri.lastfm.ui.album.detail

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPresenter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.ggasbarri.lastfm.util.snackBar
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

        setupToolbar()

        setupTracksRecyclerView()

        loadAlbumImage(parameters.memoryCacheKey)
    }

    private fun setupToolbar() {

        binding.toolbar.menu.setGroupVisible(R.id.menuSaveGroup, false)
        binding.toolbar.menu.setGroupVisible(R.id.menuDeleteGroup, false)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSave -> {
                    viewModel.saveAlbum().observe(viewLifecycleOwner, {
                        if (it.isSuccessful)
                            binding.rootLayout.snackBar(
                                R.string.album_detail_save_success
                            )
                        else if (it.hasError)
                            binding.rootLayout.snackBar(
                                R.string.album_detail_save_error
                            )
                    })
                    true
                }
                R.id.menuDelete -> {
                    viewModel.deleteAlbum().observe(viewLifecycleOwner, {
                        if (it.isSuccessful)
                            binding.rootLayout.snackBar(
                                R.string.album_detail_delete_success
                            )
                        else if (it.hasError)
                            binding.rootLayout.snackBar(
                                R.string.album_detail_delete_error
                            )
                    })
                    true
                }
                else -> false
            }
        }

        viewModel.isSavedLocally.observe(viewLifecycleOwner, Observer { isSavedLocally ->
            binding.toolbar.menu.setGroupVisible(R.id.menuSaveGroup, !isSavedLocally)
            binding.toolbar.menu.setGroupVisible(R.id.menuDeleteGroup, isSavedLocally)
        })
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