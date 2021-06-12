package com.ggasbarri.lastfm.api.models

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import java.text.SimpleDateFormat
import java.util.*


@JsonClass(generateAdapter = true)
data class AlbumInfoResponse(
    @Json(name = "album")
    val album: Album
) {

    @JsonClass(generateAdapter = true)
    data class Album(
        @Json(name = "artist")
        val artist: String,
        @Json(name = "image")
        val images: List<Image>,
        @Json(name = "listeners")
        val listeners: String,
        @Json(name = "mbid")
        val mbid: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "playcount")
        val playcount: String,
        @Json(name = "tags")
        val tagsObj: Tags,
        @Json(name = "tracks")
        val tracksObj: Tracks,
        @Json(name = "url")
        val url: String,
        @Json(name = "wiki")
        val wiki: Wiki
    ) {

        //region Utility

        fun formattedPublishDate() =
            SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US).parse(wiki.published)?.time

        //endregion

        @JsonClass(generateAdapter = true)
        data class Image(
            @Json(name = "size")
            val size: String,
            @Json(name = "#text")
            val url: String
        )

        @JsonClass(generateAdapter = true)
        data class Tags(
            @Json(name = "tag")
            val tags: List<Tag>
        ) {
            @JsonClass(generateAdapter = true)
            data class Tag(
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )
        }

        @JsonClass(generateAdapter = true)
        data class Tracks(
            @Json(name = "track")
            val tracks: List<Track>
        ) {
            @JsonClass(generateAdapter = true)
            data class Track(
                @Json(name = "artist")
                val artist: Artist,
                @Json(name = "@attr")
                val attr: Attr,
                @Json(name = "duration")
                val duration: String,
                @Json(name = "name")
                val name: String,
                @Json(name = "streamable")
                val streamable: Streamable? = null,
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
                data class Attr(
                    @Json(name = "rank")
                    val rank: String
                )

                @JsonClass(generateAdapter = true)
                data class Streamable(
                    @Json(name = "fulltrack")
                    val fulltrack: String,
                    @Json(name = "#text")
                    val text: String
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class Wiki(
            @Json(name = "content")
            val content: String,
            @Json(name = "published")
            val published: String,
            @Json(name = "summary")
            val summary: String
        )
    }
}