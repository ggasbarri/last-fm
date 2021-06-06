package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ArtistSearch(
    val results: ArtistSearchResults,
) {
    fun getArtists() = results.artistsMatches.artists
}

@JsonClass(generateAdapter = true)
data class ArtistSearchResults(
    @Json(name = "opensearch:Query")
    val query: ArtistSearchQuery,
    @Json(name = "opensearch:totalResults")
    val totalResults: Long,
    @Json(name = "opensearch:startIndex")
    val startIndex: Int,
    @Json(name = "opensearch:itemsPerPage")
    val itemsPerPage: Int,
    @Json(name = "artistmatches")
    val artistsMatches: ArtistMatches
)

@JsonClass(generateAdapter = true)
data class ArtistSearchQuery(
    val role: String,
    val searchTerms: String,
    val startPage: Int,
)

@JsonClass(generateAdapter = true)
data class ArtistMatches(
    val artists: List<Artist>
)