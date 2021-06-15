package com.ggasbarri.lastfm.ui.artist.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.repository.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ArtistSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val artistsRepository: ArtistsRepository,
) : ViewModel() {

    var artistQuery: String?
        get() = savedStateHandle[SAVED_STATE_ARTIST_QUERY]
        set(value) {
            savedStateHandle[SAVED_STATE_ARTIST_QUERY] = value
        }

    val searchResults =
        savedStateHandle.getLiveData<String>(SAVED_STATE_ARTIST_QUERY).switchMap { artistQuery ->
            when {
                artistQuery == null -> {
                    flow { emit(null) }.asLiveData()
                }
                artistQuery.isBlank() -> {
                    flow { emit(PagingData.empty<Artist>()) }.asLiveData()
                }
                else -> {
                    artistsRepository
                        .searchArtists(artistQuery)
                        .distinctUntilChanged()
                        .debounce(SEARCH_RESULTS_DEBOUNCE_MS)
                        .cachedIn(viewModelScope)
                        .asLiveData()
                }
            }
        }

    companion object {
        private const val SAVED_STATE_ARTIST_QUERY = "artist_query"
        private const val SEARCH_RESULTS_DEBOUNCE_MS = 400L
    }
}