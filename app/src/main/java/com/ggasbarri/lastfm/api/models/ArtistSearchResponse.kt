package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ArtistSearchResponse(
    @Json(name = "results")
    val results: Results
) {
    @JsonClass(generateAdapter = true)
    data class Results(
        @Json(name = "artistmatches")
        val artistMatches: ArtistMatches,
        @Json(name = "@attr")
        val attr: Attr? = null,
        @Json(name = "opensearch:itemsPerPage")
        val itemsPerPage: String,
        @Json(name = "opensearch:Query")
        val query: Query,
        @Json(name = "opensearch:startIndex")
        val startIndex: String,
        @Json(name = "opensearch:totalResults")
        val totalResults: String
    ) {
        @JsonClass(generateAdapter = true)
        data class ArtistMatches(
            @Json(name = "artist")
            val artist: List<Artist>
        ) {
            @JsonClass(generateAdapter = true)
            data class Artist(
                @Json(name = "image")
                val images: List<Image>,
                @Json(name = "listeners")
                val listeners: String,
                @Json(name = "mbid")
                val mbid: String,
                @Json(name = "name")
                val name: String,
                @Json(name = "streamable")
                val streamable: String,
                @Json(name = "url")
                val url: String
            ) {
                @JsonClass(generateAdapter = true)
                data class Image(
                    @Json(name = "size")
                    val size: String,
                    @Json(name = "#text")
                    val url: String
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class Attr(
            @Json(name = "for")
            val forX: String? = null
        )

        @JsonClass(generateAdapter = true)
        data class Query(
            @Json(name = "role")
            val role: String,
            @Json(name = "searchTerms")
            val searchTerms: String,
            @Json(name = "startPage")
            val startPage: String,
            @Json(name = "#text")
            val text: String
        )
    }
}