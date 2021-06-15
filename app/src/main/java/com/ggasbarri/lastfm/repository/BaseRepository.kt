package com.ggasbarri.lastfm.repository

import com.ggasbarri.lastfm.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        crossinline cache: suspend () -> T?,
        crossinline block: suspend () -> T
    ): Flow<Resource<T>> {
        var obtainedCache: T? = null
        return flow {
            // Notify loading state immediately
            emit(Resource.loading())

            // Load cache
            obtainedCache = cache.invoke()
            emit(Resource.loading(data = obtainedCache))

            val data = block()
            emit(Resource.success(data))
        }.catch {
            emit(Resource.error(it, data = obtainedCache))
        }
    }
}