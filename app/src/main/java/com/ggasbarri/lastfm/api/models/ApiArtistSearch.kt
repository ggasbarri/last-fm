package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiArtistSearch(
    val results: ApiArtistSearchResults,
) {
    fun getArtists() = results.artistsMatches.artists
}

@JsonClass(generateAdapter = true)
data class ApiArtistSearchResults(
    @Json(name = "opensearch:Query")
    val query: ApiArtistSearchQuery,
    @Json(name = "opensearch:totalResults")
    val totalResults: Long,
    @Json(name = "opensearch:startIndex")
    val startIndex: Int,
    @Json(name = "opensearch:itemsPerPage")
    val itemsPerPage: Int,
    @Json(name = "artistmatches")
    val artistsMatches: ApiArtistMatches
)

@JsonClass(generateAdapter = true)
data class ApiArtistSearchQuery(
    val role: String,
    val searchTerms: String,
    val startPage: Int,
)

@JsonClass(generateAdapter = true)
data class ApiArtistMatches(
    @Json(name = "artist")
    val artists: List<ApiArtist>
)