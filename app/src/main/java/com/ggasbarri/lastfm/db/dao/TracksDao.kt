package com.ggasbarri.lastfm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.Track

@Dao
interface TracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tracks: List<Track>)

    @Delete
    fun deleteAll(tracks: List<Track>)
}