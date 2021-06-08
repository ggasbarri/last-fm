package com.ggasbarri.lastfm.db.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class StringListTypeConverter {

    companion object {

        private var _moshi: Moshi? = null
        val moshi: Moshi
            get() {
                if (_moshi == null) _moshi = Moshi.Builder().build()
                return _moshi!!
            }
    }

    @TypeConverter
    fun stringListToString(stringList: List<String>): String {
        val type: Type = Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
        return moshi.adapter<List<String>>(type).toJson(stringList)
    }

    @TypeConverter
    fun stringListToString(string: String): List<String> {
        val type: Type = Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
        return moshi.adapter<List<String>>(type).fromJson(string) ?: listOf()
    }
}