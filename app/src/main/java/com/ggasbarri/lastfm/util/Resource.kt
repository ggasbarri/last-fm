package com.ggasbarri.lastfm.util

data class Resource<T>(
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

    fun <R> copyWithData(data: R?): Resource<R> = Resource(
        status = this.status,
        data = data,
        throwable = this.throwable
    )

    companion object {
        fun <T> success(data: T?): Resource<T> =
            Resource(Status.SUCCESS, data)

        fun <T> loading(data: T? = null): Resource<T> =
            Resource(Status.LOADING, data)

        fun <T> error(throwable: Throwable?, data: T? = null): Resource<T> =
            Resource(Status.ERROR, data, throwable)
    }

    enum class Status {
        SUCCESS, LOADING, ERROR
    }
}