package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Artist(
    @Json(name = "mbid")
    val id: String,
    val name: String,
    val url: String,
    val image: List<Image>,
    val listeners: Long,
    val streamable: Int,
) {
    val isStreamable: Boolean
        get() = streamable == 1
}