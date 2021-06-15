package com.ggasbarri.lastfm.ui.album.detail

import androidx.lifecycle.*
import com.ggasbarri.lastfm.repository.AlbumsRepository
import com.ggasbarri.lastfm.util.Resource
import com.ggasbarri.lastfm.util.toSingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    val isSavedLocally =
        album.map {
            it?.data?.id != null && it.data.id != 0L
        }

    val tracks =
        albumWithTracks.map {
            it?.copyWithData(it.data?.tracks)
        }

    fun saveAlbum() = flow<Resource<Long>> {
        val albumWithTracks = albumWithTracks.value?.data
        if (albumWithTracks == null) emit(Resource.error(null))
        else {
            val dbFlow = albumsRepository.saveAlbum(albumWithTracks)
                .map { newId -> Resource.success(newId) }
                .catch { throwable -> Resource.error<Long>(throwable) }

            emitAll(dbFlow)
        }
    }.asLiveData().toSingleEvent()

    fun deleteAlbum() = flow<Resource<Long>> {
        val albumWithTracks = albumWithTracks.value?.data
        if (albumWithTracks == null) emit(Resource.error(null))
        else {
            val dbFlow = albumsRepository.deleteAlbum(albumWithTracks)
                .map { newId -> Resource.success(newId) }
                .catch { throwable -> Resource.error<Long>(throwable) }

            emitAll(dbFlow)
        }
    }.asLiveData().toSingleEvent()

    companion object {
        private const val SAVED_STATE_ALBUM_REMOTE_ID = "album_remote_id"
    }
}