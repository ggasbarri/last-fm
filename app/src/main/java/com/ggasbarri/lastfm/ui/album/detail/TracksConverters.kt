@file:JvmName("TracksConverters")
package com.ggasbarri.lastfm.ui.album.detail

import java.text.DecimalFormat

fun formatRank(rank: Int): String {
    return DecimalFormat("00").format(rank)
}