package com.ggasbarri.lastfm.ui.album.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggasbarri.lastfm.databinding.SavedAlbumFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SavedAlbumFragment : Fragment() {

    companion object {
        fun newInstance() = SavedAlbumFragment()
    }

    private val viewModel: SavedAlbumViewModel by viewModels()

    @Inject
    lateinit var adapter: SavedAlbumAdapter

    lateinit var binding: SavedAlbumFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            SavedAlbumFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@SavedAlbumFragment.viewLifecycleOwner
                viewModel = this@SavedAlbumFragment.viewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.savedAlbumsRv.apply {
            adapter = this@SavedAlbumFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.savedAlbums.observe(viewLifecycleOwner, { albums ->
            lifecycleScope.launch { adapter.submitData(albums) }
        })

    }
}