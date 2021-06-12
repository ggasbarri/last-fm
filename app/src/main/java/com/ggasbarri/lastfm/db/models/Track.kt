package com.ggasbarri.lastfm.db.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tracks")
@Parcelize
data class Track(
    val name: String,
    val artist: String,
    val durationSeconds: Int,
    val rank: Int,
    val albumId: Long = 0L,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
) : Parcelable