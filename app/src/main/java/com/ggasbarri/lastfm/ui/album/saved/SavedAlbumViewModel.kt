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

    // TODO: Remove
    // Insert some albums programmatically
    /*init {
        viewModelScope.launch {
            albumsRepository.saveAlbum(
                AlbumWithTracks(
                    Album(
                        remoteId = "63b3a8ca-26f2-4e2b-b867-647a6ec2bebd",
                        name = "Believe",
                        artist = "Cher",
                        url = "https://www.last.fm/music/Cher",
                        smallImageUrl = "https://lastfm.freetls.fastly.net/i/u/174s/3b54885952161aaea4ce2965b2db1638.png",
                        largeImageUrl = "https://lastfm.freetls.fastly.net/i/u/300x300/3b54885952161aaea4ce2965b2db1638.png",
                    ),
                    tracks = listOf(
                        Track(
                            name = "Believe",
                            artist = "Cher",
                            durationSeconds = 240,
                            rank = 1
                        ),
                        Track(
                            name = "The Power",
                            artist = "Cher",
                            durationSeconds = 236,
                            rank = 2
                        ),
                    )
                )
            ).collectLatest {  }
        }
    }*/
}