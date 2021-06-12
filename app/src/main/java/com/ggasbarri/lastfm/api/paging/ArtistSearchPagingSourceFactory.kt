package com.ggasbarri.lastfm.api.paging

import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.mappings.toAppModel
import com.ggasbarri.lastfm.db.models.Artist

class ArtistSearchPagingSourceFactory(
    private val lastFmDatasource: LastFmDatasource,
    private val artist: String,
    private val limit: Int,
) : LastFmPagingSourceFactory<Artist>(
    limit = limit,
    responseBlock = { position ->
        lastFmDatasource
            .searchArtists(artist, limit = limit, page = position)
            .results
            .artistMatches
            .artist
            .toAppModel()
    }
)