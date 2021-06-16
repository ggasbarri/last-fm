package com.ggasbarri.lastfm.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ggasbarri.lastfm.databinding.InfoFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment() {

    companion object {
        fun newInstance() = InfoFragment()
    }

    private val viewModel: InfoViewModel by viewModels()

    private lateinit var binding: InfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            InfoFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@InfoFragment.viewLifecycleOwner
                viewModel = this@InfoFragment.viewModel
            }

        return binding.root
    }
}