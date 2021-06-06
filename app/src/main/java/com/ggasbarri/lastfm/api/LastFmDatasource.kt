package com.ggasbarri.lastfm.api

import com.ggasbarri.lastfm.api.models.ApiArtistSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmDatasource {

    @GET("/?${ApiMethods.SEARCH_ARTIST}")
    suspend fun searchArtists(
        @Query("artist") artist: String,
        @Query("artist") limit: Int = 30,
        @Query("artist") page: Int,
    ): ApiArtistSearch
}

const val LAST_FM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"