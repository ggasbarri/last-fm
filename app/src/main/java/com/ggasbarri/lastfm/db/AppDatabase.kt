package com.ggasbarri.lastfm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ggasbarri.lastfm.db.converters.StringListTypeConverter
import com.ggasbarri.lastfm.db.dao.AlbumsDao
import com.ggasbarri.lastfm.db.dao.ArtistsDao
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.Artist

@Database(
    entities = [Artist::class, Album::class],
    version = 1
)
@TypeConverters(StringListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistsDao(): ArtistsDao
    abstract fun albumsDao(): AlbumsDao
}