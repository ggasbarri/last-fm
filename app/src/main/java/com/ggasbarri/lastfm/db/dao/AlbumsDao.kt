package com.ggasbarri.lastfm.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.ggasbarri.lastfm.db.models.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums order by name ASC")
    fun getAll(): PagingSource<Int, Album>

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: String): Album

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(album: List<Album>)

    @Delete
    fun delete(album: Album)

    @Delete
    fun deleteAll(album: List<Album>)
}