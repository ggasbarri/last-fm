package com.ggasbarri.lastfm.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey val id: String,
    val name: String,
    val artist: String,
    val url: String,
    val smallImageUrl: String? = null,
    val largeImageUrl: String? = null,
    val publishDateMs: Long?,
    val shortDescription: String,
    val longDescription: String,
    val tags: List<String>
)