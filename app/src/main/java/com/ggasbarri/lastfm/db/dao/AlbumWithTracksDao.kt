package com.ggasbarri.lastfm.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumWithTracksDao {
    @Transaction
    @Query("SELECT * FROM albums")
    fun getAlbumsWithTracks(): PagingSource<Int, AlbumWithTracks>

    @Transaction
    @Query("SELECT * FROM albums WHERE remoteId = :albumRemoteId")
    fun getAlbumWithTracksByRemoteId(albumRemoteId: String): Flow<AlbumWithTracks?>

    @Transaction
    @Query("SELECT * FROM albums WHERE id = :albumId")
    suspend fun getAlbumWithTracksById(albumId: Int): AlbumWithTracks
}