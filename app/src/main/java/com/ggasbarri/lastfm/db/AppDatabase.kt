package com.ggasbarri.lastfm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ggasbarri.lastfm.db.models.Artist

@Database(
    entities = [Artist::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistsDao(): ArtistsDao
}