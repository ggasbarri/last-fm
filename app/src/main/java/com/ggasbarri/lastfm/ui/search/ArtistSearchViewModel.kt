package com.ggasbarri.lastfm.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.repository.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val artistsRepository: ArtistsRepository,
) : ViewModel() {

    val searchResults =
        savedStateHandle.getLiveData<String>(SAVED_STATE_ARTIST_QUERY).switchMap { artistQuery ->
            artistsRepository
                .searchArtists(artistQuery)
                .cachedIn(viewModelScope)
                .asLiveData()
        }

    fun searchArtist(artistQuery: String) {
        savedStateHandle[SAVED_STATE_ARTIST_QUERY] = artistQuery
    }

    companion object {
        const val SAVED_STATE_ARTIST_QUERY = "artist_query"
    }
}