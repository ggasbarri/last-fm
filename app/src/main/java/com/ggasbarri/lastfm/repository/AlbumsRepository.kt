package com.ggasbarri.lastfm.repository

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import coil.ImageLoader
import coil.request.ImageRequest
import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.mappings.toAppModel
import com.ggasbarri.lastfm.db.dao.AlbumWithTracksDao
import com.ggasbarri.lastfm.db.dao.AlbumsDao
import com.ggasbarri.lastfm.db.dao.TracksDao
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.db.models.AlbumWithTracks
import com.ggasbarri.lastfm.image.LocalImageInterceptor
import com.ggasbarri.lastfm.injection.IoDispatcher
import com.ggasbarri.lastfm.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val lastFmDatasource: LastFmDatasource,
    private val albumsDao: AlbumsDao,
    private val tracksDao: TracksDao,
    private val albumWithTracksDao: AlbumWithTracksDao,
    private val localImageInterceptor: LocalImageInterceptor,
    private val imageLoader: ImageLoader,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseRepository() {

    fun getSavedAlbums() = Pager(
        config = PagingConfig(
            initialLoadSize = 30,
            pageSize = 20
        ),
        pagingSourceFactory = {
            albumWithTracksDao.getAlbumsWithTracks()
        }).flow.flowOn(dispatcher)


    fun getAlbum(remoteId: String): Flow<Resource<out AlbumWithTracks>> {
        return requestWithCache(
            cacheFlow = albumWithTracksDao.getAlbumWithTracksByRemoteId(remoteId),
            requestFlow = request { lastFmDatasource.getAlbum(remoteId).toAppModel() },
            overrideResponseWithCache = { cachedData, responseData ->
                responseData?.copy(album = responseData.album.copy(id = cachedData.album.id))
            }
        ).flowOn(dispatcher)
    }


    fun saveAlbum(albumWithTracks: AlbumWithTracks, context: Context) = flow {

        val albumId = albumsDao.insert(albumWithTracks.album)

        // We need to set the newly saved ID in each track
        val tracksWithId = albumWithTracks.tracks.map {
            it.copy(albumId = albumId)
        }

        tracksDao.insertAll(tracksWithId)
        emit(albumId)
    }.onEach {
        saveImage(context, albumWithTracks.album.copy(id = it))
    }.flowOn(dispatcher)


    fun deleteAlbum(albumWithTracks: AlbumWithTracks) = flow {
        tracksDao.deleteAll(albumWithTracks.tracks)
        albumsDao.delete(albumWithTracks.album)
        emit(albumWithTracks.album.id)
    }.onEach {
        deleteImage(it)
    }.flowOn(dispatcher)


    private suspend fun saveImage(context: Context, album: Album) {
        val imageRequest = ImageRequest.Builder(context)
            .data(album)
            .build()

        imageLoader.execute(imageRequest).drawable?.let {
            localImageInterceptor.saveBitmap(
                bitmap = it.toBitmap(),
                fileName = album.id.toString()
            )
        }
    }

    private suspend fun deleteImage(albumId: Long) {
        localImageInterceptor.deleteBitmap(
            fileName = albumId.toString()
        )
    }
}