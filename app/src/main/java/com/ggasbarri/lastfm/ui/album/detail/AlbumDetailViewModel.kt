package com.ggasbarri.lastfm.ui.album.detail

import androidx.lifecycle.*
import com.ggasbarri.lastfm.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
) : ViewModel() {

    var albumRemoteId: String?
        get() = savedStateHandle[SAVED_STATE_ALBUM_REMOTE_ID]
        set(value) {
            savedStateHandle[SAVED_STATE_ALBUM_REMOTE_ID] = value
        }

    private val albumWithTracks =
        savedStateHandle.getLiveData<String>(SAVED_STATE_ALBUM_REMOTE_ID)
            .switchMap { albumRemoteId ->
                if (albumRemoteId.isNullOrBlank()) {
                    flow { emit(null) }.asLiveData()
                } else {
                    albumsRepository
                        .getAlbum(albumRemoteId)
                        .distinctUntilChanged()
                        .asLiveData()
                }
            }

    val album =
        albumWithTracks.map {
            it?.copyWithData(it.data?.album)
        }

    val tracks =
        albumWithTracks.map {
            it?.copyWithData(it.data?.tracks)
        }

    companion object {
        private const val SAVED_STATE_ALBUM_REMOTE_ID = "album_remote_id"
    }
}