package com.ggasbarri.lastfm.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ggasbarri.lastfm.db.models.AlbumWithTracks

@Dao
interface AlbumWithTracksDao {
    @Transaction
    @Query("SELECT * FROM albums")
    fun getAlbumsWithTracks(): PagingSource<Int, AlbumWithTracks>

    @Transaction
    @Query("SELECT * FROM albums WHERE remoteId = :albumRemoteId")
    suspend fun getAlbumWithTracksByRemoteId(albumRemoteId: String): AlbumWithTracks

    @Transaction
    @Query("SELECT * FROM albums WHERE id = :albumId")
    suspend fun getAlbumWithTracksById(albumId: Int): AlbumWithTracks
}