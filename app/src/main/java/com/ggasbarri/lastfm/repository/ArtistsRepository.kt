package com.ggasbarri.lastfm.repository

import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.models.ApiArtistSearch
import com.ggasbarri.lastfm.injection.IoDispatcher
import com.ggasbarri.lastfm.util.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtistsRepository @Inject constructor(
    private val lastFmDatasource: LastFmDatasource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun searchArtists(
        artist: String,
        limit: Int = 30,
        page: Int
    ): Flow<Response<ApiArtistSearch>> {
        return request {
            lastFmDatasource.searchArtists(artist, limit, page)
        }
            //.debounce(SEARCH_DEBOUNCE_MS)
            .flowOn(dispatcher)
    }

}

private const val SEARCH_DEBOUNCE_MS = 2000L