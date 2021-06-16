package com.ggasbarri.lastfm.api.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopAlbumsResponse(
    @Json(name = "topalbums")
    val topAlbums: TopAlbums
) {
    @JsonClass(generateAdapter = true)
    data class TopAlbums(
        @Json(name = "album")
        val albums: List<Album>,
        @Json(name = "@attr")
        val attr: Attr? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class Album(
            @Json(name = "artist")
            val artist: Artist,
            @Json(name = "image")
            val images: List<Image>,
            @Json(name = "mbid")
            val mbid: String? = null,
            @Json(name = "name")
            val name: String,
            @Json(name = "playcount")
            val playCount: Int,
            @Json(name = "url")
            val url: String
        ) {
            @JsonClass(generateAdapter = true)
            data class Artist(
                @Json(name = "mbid")
                val mbid: String,
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )

            @JsonClass(generateAdapter = true)
            data class Image(
                @Json(name = "size")
                val size: String,
                @Json(name = "#text")
                val url: String
            )
        }

        @JsonClass(generateAdapter = true)
        data class Attr(
            @Json(name = "artist")
            val artist: String,
            @Json(name = "page")
            val page: String,
            @Json(name = "perPage")
            val perPage: String,
            @Json(name = "total")
            val total: String,
            @Json(name = "totalPages")
            val totalPages: String
        )
    }
}