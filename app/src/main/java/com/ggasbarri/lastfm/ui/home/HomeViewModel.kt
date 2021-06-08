package com.ggasbarri.lastfm.ui.home

import androidx.lifecycle.*
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val albumsRepository: AlbumsRepository,
) : ViewModel() {

    val savedAlbums = savedStateHandle.getLiveData<List<Album>>("albums").switchMap {
        albumsRepository
            .getSavedAlbums()
            .asLiveData()
    }

    /*init {
        viewModelScope.launch {
            albumsRepository.saveAlbum(
                Album(
                    id = "1",
                    name = "Believe",
                    artist = "Cher",
                    url = "https://www.last.fm/music/Cher/Believe",
                    smallImageUrl = "https://lastfm.freetls.fastly.net/i/u/34s/3b54885952161aaea4ce2965b2db1638.png",
                    largeImageUrl = "https://lastfm.freetls.fastly.net/i/u/64s/3b54885952161aaea4ce2965b2db1638.png",
                    publishDateMs = Date().time,
                    shortDescription = "Test",
                    longDescription = "Test",
                    tags = listOf("Pop"),
                )
            ).collect {  }
        }
    }*/

}