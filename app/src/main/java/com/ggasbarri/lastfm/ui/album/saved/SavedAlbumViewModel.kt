package com.ggasbarri.lastfm.ui.album.saved

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import com.ggasbarri.lastfm.db.models.Track
import com.ggasbarri.lastfm.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedAlbumViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
) : ViewModel() {

    val savedAlbums = albumsRepository
        .getSavedAlbums()
        .cachedIn(viewModelScope)
        .asLiveData()
}