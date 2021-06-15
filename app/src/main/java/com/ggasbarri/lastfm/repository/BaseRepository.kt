package com.ggasbarri.lastfm.repository

import com.ggasbarri.lastfm.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow


abstract class BaseRepository {
    inline fun <T> request(
        initialData: T? = null,
        crossinline block: suspend () -> T
    ): Flow<Resource<T>> {
        return flow {
            emit(Resource.loading(data = initialData))
            emit(Resource.success(block()))
        }.catch {
            emit(Resource.error(it, data = initialData))
        }
    }

    inline fun <T> requestWithCache(
        cacheFlow: Flow<T?>,
        requestFlow: Flow<Resource<T>>,
        crossinline overrideResponseWithCache: (cachedData: T, responseData: T?) -> T?
    ): Flow<Resource<T>> {
        return cacheFlow.combine(requestFlow) { cache, response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    if (cache != null) Resource.loading(cache)
                    else Resource.loading()
                }
                Resource.Status.SUCCESS -> {
                    if (cache != null)
                        response.copyWithData(
                            overrideResponseWithCache(cache, response.data)
                        )
                    else response

                }
                Resource.Status.ERROR -> {
                    if (cache != null) Resource.error(response.throwable, cache)
                    else Resource.error(response.throwable)
                }
            }
        }
    }
}