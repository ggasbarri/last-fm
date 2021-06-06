package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiTrack(
    val name: String,
    val url: String,
    val duration: Int
)