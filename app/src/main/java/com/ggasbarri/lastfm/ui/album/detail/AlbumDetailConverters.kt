@file:JvmName("AlbumDetailConverters")
package com.ggasbarri.lastfm.ui.album.detail

import android.os.Build
import android.text.Html

fun shortDescriptionToHtml(htmlText: String?): String {
    return htmlText?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString()
        else
            Html.fromHtml(it).toString()
    } ?: ""
}