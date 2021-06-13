package com.ggasbarri.lastfm.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
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

        binding.artistSearchRv.apply {
            adapter = this@ArtistSearchFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.searchResults.observe(viewLifecycleOwner, { artists ->
            lifecycleScope.launch { artists?.let { adapter.submitData(it) } }
        })

        if (viewModel.artistQuery.isNullOrBlank()) {

            // Show keyboard when entering the screen
            binding.searchTextInputEditText.apply {
                if (requestFocus()) {
                    val inputMethodManager =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }
    }
}