package com.ggasbarri.lastfm.ui.artist.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.repository.ArtistsRepository
import com.ggasbarri.lastfm.util.toSingleEvent
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

    private val searchRequested = MutableLiveData<Unit>().toSingleEvent()
    val searchResults =
        savedStateHandle.getLiveData<String>(SAVED_STATE_ARTIST_QUERY).switchMap { artistQuery ->
            searchRequested.switchMap {
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
                            .cachedIn(viewModelScope)
                            .asLiveData()
                    }
                }
            }
        }

    fun search() {
        searchRequested.value = Unit
    }

    companion object {
        private const val SAVED_STATE_ARTIST_QUERY = "artist_query"
    }
}