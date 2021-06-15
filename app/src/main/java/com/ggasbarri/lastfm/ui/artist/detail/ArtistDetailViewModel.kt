package com.ggasbarri.lastfm.ui.artist.detail

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.repository.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val artistsRepository: ArtistsRepository,
) : ViewModel() {

    var artist: Artist?
        get() = savedStateHandle[SAVED_STATE_ARTIST]
        set(value) {
            savedStateHandle[SAVED_STATE_ARTIST] = value
        }

    val topAlbums =
        savedStateHandle.getLiveData<Artist>(SAVED_STATE_ARTIST).switchMap { artist ->
            if (artist == null) {
                flow { emit(null) }.asLiveData()
            } else {
                artistsRepository
                    .getTopSavedAlbums(artist.remoteId)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .asLiveData()
            }
        }

    companion object {
        private const val SAVED_STATE_ARTIST = "artist"
    }
}