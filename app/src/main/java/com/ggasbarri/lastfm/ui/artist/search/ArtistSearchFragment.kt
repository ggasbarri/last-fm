package com.ggasbarri.lastfm.ui.artist.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.memory.MemoryCache
import com.ggasbarri.lastfm.R
import com.ggasbarri.lastfm.databinding.ArtistSearchFragmentBinding
import com.ggasbarri.lastfm.databinding.ItemArtistSearchResultBinding
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.util.ItemClickListener
import com.ggasbarri.lastfm.util.MemoryCacheKey
import com.ggasbarri.lastfm.util.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArtistSearchFragment : Fragment() {

    companion object {
        fun newInstance() = ArtistSearchFragment()
    }

    @Inject
    lateinit var adapter: ArtistSearchAdapter

    lateinit var binding: ArtistSearchFragmentBinding

    private val viewModel: ArtistSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            ArtistSearchFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@ArtistSearchFragment.viewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArtistsRecyclerView()

        if (viewModel.artistQuery.isNullOrBlank()) {
            // Show keyboard when entering the screen
            focusSearchEditText()
        }

        // Setup Empty text & ProgressBar
        handleLoadingSate()
    }

    private fun setupArtistsRecyclerView() {

        viewModel.searchResults.observe(viewLifecycleOwner, { artists ->
            lifecycleScope.launch { artists?.let { adapter.submitData(it) } }
        })

        binding.artistSearchRv.apply {
            this@ArtistSearchFragment.adapter.onItemClickListener =
                object : ItemClickListener<ItemArtistSearchResultBinding, Artist> {
                    override fun onItemClick(
                        binding: ItemArtistSearchResultBinding,
                        item: Artist,
                        position: Int
                    ) {
                        if (item.remoteId.isBlank()) {
                            this@ArtistSearchFragment.binding.rootLayout.snackBar(
                                R.string.artist_search_artist_not_in_server_error
                            )
                        } else {
                            findNavController().navigate(
                                directions = ArtistSearchFragmentDirections
                                    .actionArtistSearchFragmentToArtistDetailFragment(
                                        item,
                                        MemoryCacheKey(binding.imageCacheKey ?: MemoryCache.Key(""))
                                    )
                            )
                        }
                    }
                }

            adapter = this@ArtistSearchFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        // Add separators
        binding.artistSearchRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        // Ensure first position on RecyclerView when query changes
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.artistSearchRv.scrollToPosition(0)
                }
            }
        })
    }

    private fun handleLoadingSate() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadingState ->
                binding.searchProgressBar.isVisible = loadingState.refresh is LoadState.Loading
                binding.searchEmptyTv.isVisible = adapter.itemCount < 1
                binding.searchEmptyTv.text =
                    if (!viewModel.artistQuery.isNullOrBlank() && loadingState.refresh !is LoadState.Loading)
                        getString(R.string.search_artists_empty_text)
                    else
                        getString(R.string.search_artists_start_text)
            }
        }
    }

    private fun focusSearchEditText() {
        binding.searchTextInputEditText.apply {
            if (requestFocus()) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}