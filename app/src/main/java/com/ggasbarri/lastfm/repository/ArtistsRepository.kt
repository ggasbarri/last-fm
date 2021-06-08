package com.ggasbarri.lastfm.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.mappings.toAppModel
import com.ggasbarri.lastfm.api.paging.ArtistSearchPagingSourceFactory
import com.ggasbarri.lastfm.db.models.Artist
import com.ggasbarri.lastfm.injection.IoDispatcher
import com.ggasbarri.lastfm.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ArtistsRepository @Inject constructor(
    private val lastFmDatasource: LastFmDatasource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun searchArtists(
        artist: String,
        limit: Int = 30
    ): Flow<Resource<PagingData<Artist>>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = 30,
                pageSize = limit
            ),
            pagingSourceFactory = {
                ArtistSearchPagingSourceFactory(lastFmDatasource, artist, limit)
            }).flow
            .transform {
                emit(Resource.success(it))
            }
            .onStart {
                emit(Resource.loading())
            }
            .catch { throwable ->
                emit(Resource.error(throwable))
            }
            //.debounce(SEARCH_DEBOUNCE_MS)
            .flowOn(dispatcher)
    }

}

private const val SEARCH_DEBOUNCE_MS = 2000L