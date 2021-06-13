package com.ggasbarri.lastfm.api.mappings

import com.ggasbarri.lastfm.api.models.ArtistSearchResponse
import com.ggasbarri.lastfm.db.models.Artist

fun ArtistSearchResponse.Results.ArtistMatches.Artist.toAppModel(): Artist {
    return Artist(
        remoteId = mbid,
        name = name,
        url = url,
        smallImageUrl = images.firstOrNull { it.size == "large" }?.url,
        largeImageUrl = images.firstOrNull { it.size == "extralarge" }?.url,
        totalListeners = try { this.listeners.toInt() } catch (e: Throwable) { null },
    )
}

fun List<ArtistSearchResponse.Results.ArtistMatches.Artist>.toAppModel() = map { it.toAppModel() }