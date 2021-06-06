package com.ggasbarri.lastfm.repository

import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.db.dao.AlbumsDao
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.injection.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val lastFmDatasource: LastFmDatasource,
    private val albumsDao: AlbumsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun getSavedAlbums() = albumsDao.getAll()

    suspend fun saveAlbum(album: Album) = albumsDao.insert(album)

}