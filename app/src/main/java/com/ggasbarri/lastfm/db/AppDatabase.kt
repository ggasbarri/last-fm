package com.ggasbarri.lastfm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ggasbarri.lastfm.db.converters.StringListTypeConverter
import com.ggasbarri.lastfm.db.dao.AlbumWithTracksDao
import com.ggasbarri.lastfm.db.dao.AlbumsDao
import com.ggasbarri.lastfm.db.dao.ArtistsDao
import com.ggasbarri.lastfm.db.dao.TracksDao
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.db.models.Track

@Database(
    entities = [Artist::class, Album::class, Track::class],
    version = 1
)
@TypeConverters(StringListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistsDao(): ArtistsDao
    abstract fun albumsDao(): AlbumsDao
    abstract fun tracksDao(): TracksDao
    abstract fun albumWithTracksDao(): AlbumWithTracksDao
}