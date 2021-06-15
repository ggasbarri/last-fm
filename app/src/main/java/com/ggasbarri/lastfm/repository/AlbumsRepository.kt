package com.ggasbarri.lastfm.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.mappings.toAppModel
import com.ggasbarri.lastfm.db.dao.AlbumWithTracksDao
import com.ggasbarri.lastfm.db.dao.AlbumsDao
import com.ggasbarri.lastfm.db.dao.TracksDao
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import com.ggasbarri.lastfm.injection.IoDispatcher
import com.ggasbarri.lastfm.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val lastFmDatasource: LastFmDatasource,
    private val albumsDao: AlbumsDao,
    private val tracksDao: TracksDao,
    private val albumWithTracksDao: AlbumWithTracksDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    fun getSavedAlbums() = Pager(
        config = PagingConfig(
            initialLoadSize = 30,
            pageSize = 20
        ),
        pagingSourceFactory = {
            albumWithTracksDao.getAlbumsWithTracks()
        }).flow.flowOn(dispatcher)


    fun saveAlbum(albumWithTracks: AlbumWithTracks) = flow {

        val albumId = albumsDao.insert(albumWithTracks.album)

        // We need to set the newly saved ID in each track
        val tracksWithId = albumWithTracks.tracks.map {
            it.copy(albumId = albumId)
        }

        tracksDao.insertAll(tracksWithId)
        emit(Unit)
    }.flowOn(dispatcher)


    fun getAlbum(remoteId: String): Flow<Resource<AlbumWithTracks>> {
        return requestWithCache(
            cache = {
                albumWithTracksDao.getAlbumWithTracksByRemoteId(remoteId)
            }
        ) {
            lastFmDatasource.getAlbum(remoteId).toAppModel()
        }.flowOn(dispatcher)
    }
}