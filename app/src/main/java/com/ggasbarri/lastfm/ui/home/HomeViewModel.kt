package com.ggasbarri.lastfm.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
) : ViewModel() {

    val savedAlbums = albumsRepository
        .getSavedAlbums()
        .cachedIn(viewModelScope)
        .asLiveData()

}