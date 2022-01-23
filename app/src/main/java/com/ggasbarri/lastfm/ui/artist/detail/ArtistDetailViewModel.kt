package com.ggasbarri.lastfm.ui.artist.detail

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.repository.ArtistsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*

class ArtistDetailViewModel @AssistedInject constructor(
    artistsRepository: ArtistsRepository,
    @Assisted artist: Artist,
) : ViewModel() {
    val topAlbums = artistsRepository
        .getTopSavedAlbums(artist.remoteId)
        .distinctUntilChanged()
        .cachedIn(viewModelScope)
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    @AssistedFactory
    interface ArtistDetailViewModelFactory {
        fun create(artist: Artist): ArtistDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: ArtistDetailViewModelFactory,
            artist: Artist
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(artist) as T
            }
        }
    }
}