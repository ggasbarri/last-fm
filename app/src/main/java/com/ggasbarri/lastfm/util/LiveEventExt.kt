package com.ggasbarri.lastfm.util

import androidx.lifecycle.LiveData
import com.hadilq.liveevent.LiveEvent


fun <T> LiveData<T>.toSingleEvent(): LiveEvent<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}