package com.ggasbarri.lastfm.db.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "albums")
@Parcelize
data class Album(
    val remoteId: String,
    val name: String,
    val artist: String,
    val url: String,
    val smallImageUrl: String? = null,
    val largeImageUrl: String? = null,
    val publishDateMs: Long? = null,
    val shortDescription: String? = null,
    val longDescription: String? = null,
    val tags: List<String>? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
) : Parcelable