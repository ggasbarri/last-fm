@file:JvmName("AlbumDetailConverters")

package com.ggasbarri.lastfm.ui.album.detail

import android.os.Build
import android.text.Html

fun shortDescriptionToHtml(htmlText: String?, emptyText: String): String {
    return when {
        htmlText.isNullOrBlank() -> emptyText
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY).toString()
        else -> Html.fromHtml(htmlText).toString()
    }

}