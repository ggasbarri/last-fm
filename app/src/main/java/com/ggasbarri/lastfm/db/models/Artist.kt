package com.ggasbarri.lastfm.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    val remoteId: String,
    val name: String,
    val url: String,
    val smallImageUrl: String? = null,
    val largeImageUrl: String? = null,
    val totalListeners: Int? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
)