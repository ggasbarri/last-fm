package com.ggasbarri.lastfm.api.mappings

import com.ggasbarri.lastfm.api.models.ApiArtist
import com.ggasbarri.lastfm.api.models.getLargeImage
import com.ggasbarri.lastfm.api.models.getSmallImage
import com.ggasbarri.lastfm.db.models.Artist

fun ApiArtist.toAppModel(): Artist {
    return Artist(
        id = mbid,
        name = name,
        url = url,
        smallImageUrl = images.getSmallImage()?.url,
        largeImageUrl = images.getLargeImage()?.url,
        totalListeners = listeners,
    )
}

fun List<ApiArtist>.toAppModel() = map { it.toAppModel() }