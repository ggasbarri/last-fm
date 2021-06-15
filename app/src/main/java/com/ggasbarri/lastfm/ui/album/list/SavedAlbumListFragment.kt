package com.ggasbarri.lastfm.ui.album.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import coil.memory.MemoryCache
import com.ggasbarri.lastfm.databinding.ItemSavedAlbumBinding
import com.ggasbarri.lastfm.databinding.SavedAlbumFragmentBinding
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import com.ggasbarri.lastfm.util.ItemClickListener
import com.ggasbarri.lastfm.util.MemoryCacheKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SavedAlbumListFragment : Fragment() {

    companion object {
        fun newInstance() = SavedAlbumListFragment()
    }

    private val listViewModel: SavedAlbumListViewModel by viewModels()

    @Inject
    lateinit var adapter: SavedAlbumAdapter

    lateinit var binding: SavedAlbumFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            SavedAlbumFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@SavedAlbumListFragment.viewLifecycleOwner
                viewModel = this@SavedAlbumListFragment.listViewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSavedAlbumsRv()

        handleLoadingSate()
    }

    private fun setupSavedAlbumsRv() {

        binding.savedAlbumsRv.apply {
            this@SavedAlbumListFragment.adapter.onItemClickListener =
                object : ItemClickListener<ItemSavedAlbumBinding, AlbumWithTracks> {
                    override fun onItemClick(
                        binding: ItemSavedAlbumBinding,
                        item: AlbumWithTracks,
                        position: Int
                    ) {
                        findNavController().navigate(
                            directions = SavedAlbumListFragmentDirections
                                .actionSavedAlbumsFragmentToAlbumDetailFragment(
                                    item.album.remoteId ?: "",
                                    binding.memoryCacheKey ?: MemoryCacheKey(
                                        MemoryCache.Key("")
                                    )
                                )
                        )
                    }
                }

            adapter = this@SavedAlbumListFragment.adapter
            layoutManager =
                GridLayoutManager(requireContext(), 2)
        }

        listViewModel.savedAlbums.observe(viewLifecycleOwner, { albums ->
            lifecycleScope.launch { adapter.submitData(albums) }
        })

    }

    private fun handleLoadingSate() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadingState ->
                binding.savedAlbumsEmptyTv.isVisible =
                    adapter.itemCount < 1 && loadingState.refresh !is LoadState.Loading
            }
        }
    }
}