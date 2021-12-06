package com.chan.movie.ui.main.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PageData(
    var start: Int = DEFAULT_START,
    var display: Int = DEFAULT_DISPLAY,
    var total: Int = DEFAULT_TOTAL
) : Parcelable {
    companion object {
        const val DEFAULT_START = 1
        const val DEFAULT_DISPLAY = 20
        const val DEFAULT_TOTAL = 0
    }
}