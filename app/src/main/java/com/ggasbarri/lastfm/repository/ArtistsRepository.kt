package com.ggasbarri.lastfm.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.paging.ArtistSearchPagingSourceFactory
import com.ggasbarri.lastfm.api.paging.ArtistTopAlbumsPagingSourceFactory
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ArtistsRepository @Inject constructor(
    private val lastFmDatasource: LastFmDatasource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    fun searchArtists(
        artist: String,
        limit: Int = 30
    ): Flow<PagingData<Artist>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = limit,
                pageSize = limit
            ),
            pagingSourceFactory = {
                ArtistSearchPagingSourceFactory(lastFmDatasource, artist, limit)
            }).flow
            //.debounce(SEARCH_DEBOUNCE_MS)
            .flowOn(dispatcher)
    }

    fun getTopSavedAlbums(
        artistRemoteId: String,
        limit: Int = 30
    ): Flow<PagingData<Album>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = limit,
                pageSize = limit
            ),
            pagingSourceFactory = {
                ArtistTopAlbumsPagingSourceFactory(lastFmDatasource, artistRemoteId, limit)
            }).flow
            .flowOn(dispatcher)
    }
}

private const val SEARCH_DEBOUNCE_MS = 2000L