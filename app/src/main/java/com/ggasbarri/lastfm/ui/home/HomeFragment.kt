package com.ggasbarri.lastfm.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ggasbarri.lastfm.R
import com.ggasbarri.lastfm.databinding.HomeFragmentBinding
import com.ggasbarri.lastfm.ui.search.ArtistSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: SavedAlbumAdapter

    lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            HomeFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@HomeFragment.viewLifecycleOwner
                viewModel = this@HomeFragment.viewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.savedAlbumsRv.apply {
            adapter = this@HomeFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.savedAlbums.observe(viewLifecycleOwner, { albums ->
            lifecycleScope.launch { adapter.submitData(albums) }
        })

    }
}