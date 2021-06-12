package com.ggasbarri.lastfm.repository

import com.ggasbarri.lastfm.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseRepository {
    suspend inline fun <T> request(
        initialData: T? = null,
        crossinline block: suspend () -> T
    ): Flow<Resource<T>> {
        return flow {
            emit(Resource.loading(data = initialData))

            try {
                val data = block()
                emit(Resource.success(data))
            } catch (throwable: Throwable) {
                emit(Resource.error(throwable, data = initialData))
            }
        }
    }

    suspend inline fun <T> requestWithCache(
        crossinline cache: suspend () -> T?,
        crossinline block: suspend () -> T
    ): Flow<Resource<T>> {
        return flow {
            // Notify loading state immediately
            emit(Resource.loading<T>())

            // Load cache
            val cachedData = cache.invoke()
            emit(Resource.loading(data = cachedData))

            try {
                val data = block()
                emit(Resource.success(data))
            } catch (throwable: Throwable) {
                emit(Resource.error(throwable, data = cachedData))
            }
        }
    }
}