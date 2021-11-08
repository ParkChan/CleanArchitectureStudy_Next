package com.chan.moviesearcher.ui.data

class PageInfo(private val pageData: PageData) {

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
        return pageData.start.plus(pageData.display).minus(1) <= pageData.total
    }

    fun nextPage(): Int = pageData.start.plus(pageData.display)
    fun startPage(): Int = pageData.start

}