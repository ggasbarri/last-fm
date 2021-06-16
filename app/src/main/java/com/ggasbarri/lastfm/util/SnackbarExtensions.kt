package com.ggasbarri.lastfm.util

import android.view.View
import androidx.annotation.IntegerRes
import com.google.android.material.snackbar.Snackbar

inline fun View.snackBar(
    @IntegerRes messageRes: Int,
    length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit = {}
) {
    snackBar(resources.getString(messageRes), length, f)
}

inline fun View.snackBar(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit = {}
) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}