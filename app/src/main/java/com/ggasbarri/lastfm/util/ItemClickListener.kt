package com.ggasbarri.lastfm.util

import androidx.databinding.ViewDataBinding

interface ItemClickListener<T : ViewDataBinding, R> {
    fun onItemClick(binding: T, item: R, position: Int)
}