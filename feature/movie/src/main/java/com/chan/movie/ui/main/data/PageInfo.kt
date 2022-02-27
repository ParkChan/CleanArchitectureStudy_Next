package com.chan.movie.ui.main.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal class PageInfo(private val pageData: PageData) : Parcelable {

    fun init(): PageInfo {
        return PageInfo(
            pageData.apply {
                start = PageData.DEFAULT_START
                display = PageData.DEFAULT_DISPLAY
                total = 0
            }
        )
    }

    fun pageInfo(start: Int, total: Int): PageInfo {
        return PageInfo(
            pageData.apply {
                this.start = start
                this.total = total
            })
    }

    fun isPaging(): Boolean {
        return nextPage().minus(1) <= pageData.total
                && nextPage() <= DEFAULT_START_MAX
    }

    fun nextPage(): Int = pageData.start.plus(pageData.display)
    fun startPage(): Int = pageData.start

    companion object {
        private const val DEFAULT_START_MAX = 1_000
    }

}