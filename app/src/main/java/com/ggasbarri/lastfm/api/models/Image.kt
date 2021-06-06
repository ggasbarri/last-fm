package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "size")
    val sizeStr: String = "small",
    @Json(name = "#text")
    val url: String
) {
    fun getSize(): Size = Size.fromName(sizeStr)

    enum class Size(val sizeName: String) {
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large"),
        EXTRALARGE("extralarge"),
        MEGA("mega");

        companion object {
            fun fromName(sizeName: String): Size =
                values().first { it.sizeName == sizeName }
        }
    }
}