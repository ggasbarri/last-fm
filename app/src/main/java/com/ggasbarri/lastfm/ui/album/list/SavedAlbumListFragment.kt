package com.ggasbarri.lastfm.ui.album.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ggasbarri.lastfm.databinding.SavedAlbumFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
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

        binding.savedAlbumsRv.apply {
            adapter = this@SavedAlbumListFragment.adapter
            layoutManager =
                GridLayoutManager(requireContext(), 2)
        }

        listViewModel.savedAlbums.observe(viewLifecycleOwner, { albums ->
            lifecycleScope.launch { adapter.submitData(albums) }
        })

    }
}