package com.ggasbarri.lastfm.api

import com.ggasbarri.lastfm.api.models.ArtistSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmDatasource {

    @GET("/?${ApiMethods.SEARCH_ARTIST}")
    suspend fun searchArtists(@Query("artist") artist: String): ArtistSearch
}