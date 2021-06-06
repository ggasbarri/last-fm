package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*


@JsonClass(generateAdapter = true)
data class ApiAlbum(
    val mbid: String,
    val name: String,
    val artist: String,
    val url: String,
    @Json(name = "image")
    val images: List<ApiImage>,
    val listeners: Long,
    @Json(name = "playcount")
    val timesPlayed: Long,
    @Json(name = "tracks")
    val tracksObj: ApiTrackObj,
    @Json(name = "tags")
    val tagsObj: ApiTagObj,
    val wiki: ApiAlbumWiki
) {
    fun formattedPublishDate() =
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US).parse(wiki.published)?.time
}

@JsonClass(generateAdapter = true)
data class ApiTrackObj(
    @Json(name = "track")
    val tracks: List<ApiTrack>
)

@JsonClass(generateAdapter = true)
data class ApiTagObj(
    @Json(name = "tag")
    val tags: List<ApiTag>
)

@JsonClass(generateAdapter = true)
data class ApiTag(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class ApiAlbumWiki(
    val published: String, // Format: "27 Jul 2008, 15:55"
    val summary: String,
    val content: String
)