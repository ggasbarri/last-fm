package com.ggasbarri.lastfm.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggasbarri.lastfm.databinding.ArtistSearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
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
    ): View? {
        binding =
            ArtistSearchFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@ArtistSearchFragment.viewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.artistSearchRv.apply {
            adapter = this@ArtistSearchFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.searchResults.observe(viewLifecycleOwner, { artists ->
            lifecycleScope.launch { adapter.submitData(artists) }
        })

        viewModel.searchArtist("Cher")

    }
}