package com.ggasbarri.lastfm.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ggasbarri.lastfm.db.models.Album
import kotlinx.coroutines.flow.Flow

interface AlbumsDao {
    @Query("SELECT * FROM albums order by name ASC")
    suspend fun getAll(): Flow<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(album: List<Album>)

    @Delete
    fun delete(album: Album)

    @Delete
    fun deleteAll(album: List<Album>)
}