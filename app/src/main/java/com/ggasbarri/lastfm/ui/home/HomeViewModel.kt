package com.ggasbarri.lastfm.ui.home

import androidx.lifecycle.ViewModel
import com.ggasbarri.lastfm.repository.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val artistsRepository: ArtistsRepository
) : ViewModel() {

}