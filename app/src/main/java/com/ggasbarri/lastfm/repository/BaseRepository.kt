package com.ggasbarri.lastfm.repository

import com.ggasbarri.lastfm.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class BaseRepository {
    suspend inline fun <T> request(
        initialData: T? = null,
        crossinline block: suspend () -> T
    ): Flow<Response<T>> {
        return flow {
            emit(Response.loading(data = initialData))

            try {
                val data = block()
                emit(Response.success(data))
            } catch (throwable: Throwable) {
                emit(Response.error(throwable, data = initialData))
            }
        }
    }
}