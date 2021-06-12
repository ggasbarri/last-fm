package com.ggasbarri.lastfm.api.mappings

import com.ggasbarri.lastfm.api.models.AlbumInfoResponse

import com.ggasbarri.lastfm.db.models.Track


fun AlbumInfoResponse.Album.Tracks.Track.toAppModel(): Track {
    return Track(
        name = name,
        artist = artist.name,
        durationSeconds = duration.toInt(),
        rank = attr.rank.toInt(),
    )
}

@JvmName("albumInfoResponseTracksToAppModel")
fun List<AlbumInfoResponse.Album.Tracks.Track>.toAppModel() = map { it.toAppModel() }