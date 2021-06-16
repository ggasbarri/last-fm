package com.ggasbarri.lastfm.ui.album.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedAlbumListViewModel @Inject constructor(
    albumsRepository: AlbumsRepository,
) : ViewModel() {

    val savedAlbums = albumsRepository
        .getSavedAlbums()
        .cachedIn(viewModelScope)
        .asLiveData()
}