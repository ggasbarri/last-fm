package com.ggasbarri.lastfm.util

class Response<T>(
    val status: Status,
    val data: T?,
    val throwable: Throwable? = null
) {

    // Some idiomatic getters
    val isSuccessful: Boolean
        get() = status == Status.SUCCESS
    val isLoading: Boolean
        get() = status == Status.LOADING
    val hasError: Boolean
        get() = status == Status.ERROR

    companion object {
        fun <T> success(data: T?): Response<T> =
            Response(Status.SUCCESS, data)

        fun <T> loading(data: T? = null): Response<T> =
            Response(Status.LOADING, data)

        fun <T> error(throwable: Throwable?, data: T? = null): Response<T> =
            Response(Status.ERROR, data, throwable)
    }

    enum class Status {
        SUCCESS, LOADING, ERROR
    }
}