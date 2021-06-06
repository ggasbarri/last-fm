package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiAlbum(
    @Json(name = "mbid")
    val id: String,
    val name: String,
    val artist: String,
    val url: String,
    val apiImage: List<ApiImage>,
    val streamable: Int,
)