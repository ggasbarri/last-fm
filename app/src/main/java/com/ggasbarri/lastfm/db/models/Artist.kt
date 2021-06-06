package com.ggasbarri.lastfm.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ggasbarri.lastfm.api.models.ApiImage

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val id: String,
    val name: String,
    val url: String,
    val smallImageUrl: String? = null,
    val largeImageUrl: String? = null,
    val totalListeners: Long
)