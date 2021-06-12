package com.ggasbarri.lastfm.db.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize


@Parcelize
data class AlbumWithTracks(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val tracks: List<Track>
) : Parcelable