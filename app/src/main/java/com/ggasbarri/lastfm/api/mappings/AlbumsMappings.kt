package com.ggasbarri.lastfm.api.mappings

import com.ggasbarri.lastfm.api.models.ApiAlbum
import com.ggasbarri.lastfm.api.models.getLargeImage
import com.ggasbarri.lastfm.api.models.getSmallImage
import com.ggasbarri.lastfm.db.models.Album


fun ApiAlbum.toAppModel(): Album {
    return Album(
        id = mbid,
        name = name,
        url = url,
        smallImageUrl = images.getSmallImage()?.url,
        largeImageUrl = images.getLargeImage()?.url,
        artist = artist,
        publishDateMs = formattedPublishDate(),
        shortDescription = wiki.summary,
        longDescription = wiki.content,
        tags = tagsObj.tags.map { it.name },
    )
}

fun List<ApiAlbum>.toAppModel() = map { it.toAppModel() }