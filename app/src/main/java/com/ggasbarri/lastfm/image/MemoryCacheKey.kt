package com.ggasbarri.lastfm.image

import android.os.Parcelable
import coil.memory.MemoryCache
import kotlinx.parcelize.Parcelize

/** Navigation Component cannot work well with inner classes so
 * we store the cache key here
 */
@Parcelize
data class MemoryCacheKey(
    val memoryCacheKey: MemoryCache.Key
) : Parcelable