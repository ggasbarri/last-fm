package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiArtist(
    val mbid: String,
    val name: String,
    val url: String,
    @Json(name = "image")
    val images: List<ApiImage>,
    val listeners: Long,
    val streamable: Int,
)