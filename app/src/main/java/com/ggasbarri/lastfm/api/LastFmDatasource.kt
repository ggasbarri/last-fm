package com.ggasbarri.lastfm.api

import com.ggasbarri.lastfm.api.models.ApiAlbum
import com.ggasbarri.lastfm.api.models.ApiArtistSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmDatasource {

    @GET("?method=${ApiMethods.ARTIST_SEARCH}")
    suspend fun searchArtists(
        @Query("artist") artist: String,
        @Query("limit") limit: Int = 30,
        @Query("page") page: Int,
    ): ApiArtistSearch

    @GET("?method=${ApiMethods.ALBUM_GET}")
    suspend fun getAlbum(
        @Query("mbid") id: String,
    ): ApiAlbum
}

const val LAST_FM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"