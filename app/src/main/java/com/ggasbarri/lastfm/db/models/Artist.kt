package com.ggasbarri.lastfm.db.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "artists")
@Parcelize
data class Artist(
    val remoteId: String,
    val name: String,
    val url: String,
    val smallImageUrl: String? = null,
    val largeImageUrl: String? = null,
    val totalListeners: Int? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
) : Parcelable