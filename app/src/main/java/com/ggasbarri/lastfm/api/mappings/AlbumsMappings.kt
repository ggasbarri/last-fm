package com.ggasbarri.lastfm.api.mappings

import com.ggasbarri.lastfm.api.models.AlbumInfoResponse
import com.ggasbarri.lastfm.api.models.TopAlbumsResponse
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.AlbumWithTracks


fun AlbumInfoResponse.Album.toAppModel(): Album {
    return Album(
        remoteId = mbid,
        name = name,
        url = url,
        smallImageUrl = images.firstOrNull { it.size == "large" }?.url,
        largeImageUrl = images.firstOrNull { it.size == "extralarge" }?.url,
        artist = artist,
        publishDateMs = formattedPublishDate(),
        shortDescription = wiki.summary,
        longDescription = wiki.content,
        tags = tagsObj.tags.map { it.name },
    )
}

@JvmName("albumInfoResponseToAppModel")
fun List<AlbumInfoResponse.Album>.toAppModel() = map { it.toAppModel() }

fun AlbumInfoResponse.toAppModel(): AlbumWithTracks {
    return AlbumWithTracks(
        album = with(album) {
            Album(
                remoteId = mbid,
                name = name,
                url = url,
                smallImageUrl = images.firstOrNull { it.size == "large" }?.url,
                largeImageUrl = images.firstOrNull { it.size == "extralarge" }?.url,
                artist = artist,
                publishDateMs = formattedPublishDate(),
                shortDescription = wiki.summary,
                longDescription = wiki.content,
                tags = tagsObj.tags.map { it.name },
            )
        },
        tracks = album.tracksObj.tracks.toAppModel()
    )
}

@JvmName("albumInfoResponseToAppModelWithTracks")
fun List<AlbumInfoResponse>.toAppModel() = map { it.toAppModel() }

fun TopAlbumsResponse.TopAlbums.Album.toAppModel(): Album {
    return Album(
        remoteId = mbid,
        name = name,
        url = url,
        smallImageUrl = images.firstOrNull { it.size == "large" }?.url,
        largeImageUrl = images.firstOrNull { it.size == "extralarge" }?.url,
        artist = artist.name,
        publishDateMs = null,
        shortDescription = null,
        longDescription = null,
        tags = null,
    )
}

@JvmName("topAlbumsResponseToAppModel")
fun List<TopAlbumsResponse.TopAlbums.Album>.toAppModel() = map { it.toAppModel() }