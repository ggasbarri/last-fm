package com.ggasbarri.lastfm.api.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ggasbarri.lastfm.api.LastFmDatasource
import com.ggasbarri.lastfm.api.mappings.toAppModel
import com.ggasbarri.lastfm.db.models.Artist
import retrofit2.HttpException
import java.io.IOException

class ArtistSearchPagingSourceFactory(
    private val lastFmDatasource: LastFmDatasource,
    private val artist: String,
    private val limit: Int,
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val nextPage = params.key ?: 0

        return try {
            val response = lastFmDatasource.searchArtists(artist, limit = limit, page = nextPage)

            LoadResult.Page(
                data = response.getArtists().toAppModel(),
                prevKey = null, // Only paging forward
                nextKey = if (response.getArtists().size < limit) null else nextPage
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}