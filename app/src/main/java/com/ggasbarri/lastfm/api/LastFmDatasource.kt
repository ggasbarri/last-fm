package com.ggasbarri.lastfm.api

import com.ggasbarri.lastfm.api.models.AlbumInfoResponse
import com.ggasbarri.lastfm.api.models.ArtistSearchResponse
import com.ggasbarri.lastfm.api.models.TopAlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmDatasource {

    @GET("?method=${ApiMethods.ARTIST_SEARCH}")
    suspend fun searchArtists(
        @Query("artist") artist: String,
        @Query("limit") limit: Int = 30,
        @Query("page") page: Int,
    ): ArtistSearchResponse

    @GET("?method=${ApiMethods.ALBUM_INFO}")
    suspend fun getAlbum(
        @Query("mbid") id: String,
    ): AlbumInfoResponse

    @GET("?method=${ApiMethods.ALBUM_INFO}")
    suspend fun getTopAlbums(
        @Query("mbid") artistId: String,
        @Query("limit") limit: Int = 30,
        @Query("page") page: Int,
    ): TopAlbumsResponse
}

const val LAST_FM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"