package com.chan.movie.ui.main.data

data class PageData(
    var start: Int = DEFAULT_START,
    var display: Int = DEFAULT_DISPLAY,
    var total: Int = DEFAULT_TOTAL
) {
    companion object {
        const val DEFAULT_START = 1
        const val DEFAULT_DISPLAY = 20
        const val DEFAULT_TOTAL = 0
    }
}