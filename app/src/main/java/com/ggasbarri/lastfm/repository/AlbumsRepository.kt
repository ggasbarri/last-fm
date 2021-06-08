package com.ggasbarri.lastfm.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.mappings.toAppModel
import com.ggasbarri.lastfm.db.dao.AlbumsDao
import com.ggasbarri.lastfm.db.models.Album
import com.ggasbarri.lastfm.injection.IoDispatcher
import com.ggasbarri.lastfm.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val lastFmDatasource: LastFmDatasource,
    private val albumsDao: AlbumsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    fun getSavedAlbums() = Pager(
        config = PagingConfig(
            initialLoadSize = 30,
            pageSize = 20
        ),
        pagingSourceFactory = {
            albumsDao.getAll()
        }).flow.flowOn(dispatcher)

    suspend fun saveAlbum(album: Album) = flow {
        emit(albumsDao.insert(album))
    }.flowOn(dispatcher)

    suspend fun getAlbum(id: String): Flow<Resource<Album>> {
        return requestWithCache(
            cache = { albumsDao.getAlbumById(id) }
        ) {
            lastFmDatasource.getAlbum(id).toAppModel()
        }.flowOn(dispatcher)
    }

}