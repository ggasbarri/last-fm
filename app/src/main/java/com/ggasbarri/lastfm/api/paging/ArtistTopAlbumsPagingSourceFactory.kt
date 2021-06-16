package com.ggasbarri.lastfm.api.paging

import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.mappings.toAppModel
import com.ggasbarri.lastfm.db.models.Album

class ArtistTopAlbumsPagingSourceFactory(
    private val lastFmDatasource: LastFmDatasource,
    private val artistRemoteId: String,
    private val limit: Int,
) : LastFmPagingSourceFactory<Album>(
    limit = limit,
    responseBlock = { position ->
        lastFmDatasource
            .getTopAlbums(artistId = artistRemoteId, limit = limit, page = position)
            .topAlbums
            .albums
            .toAppModel()
    }
)