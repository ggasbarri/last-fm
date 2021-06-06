package com.ggasbarri.lastfm.db

import androidx.room.*
import com.ggasbarri.lastfm.db.models.Artist

@Dao
interface  ArtistsDao {
    @Query("SELECT * FROM artists order by name ASC")
    fun getAll(): List<Artist>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Artist>)

    @Delete
    fun delete(movie: Artist)

    @Delete
    fun deleteAll(movie: List<Artist>)
}