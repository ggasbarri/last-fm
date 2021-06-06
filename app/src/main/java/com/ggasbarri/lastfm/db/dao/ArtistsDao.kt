package com.ggasbarri.lastfm.db.dao

import androidx.room.*
import com.ggasbarri.lastfm.db.models.Artist

@Dao
interface ArtistsDao {
    @Query("SELECT * FROM artists order by name ASC")
    fun getAll(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artist: List<Artist>)

    @Delete
    fun delete(artist: Artist)

    @Delete
    fun deleteAll(artist: List<Artist>)
}