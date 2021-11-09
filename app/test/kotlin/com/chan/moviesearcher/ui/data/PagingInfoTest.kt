package com.chan.moviesearcher.ui.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PagingInfoTest {

    private lateinit var pageInfo: PageInfo

    @BeforeEach
    fun setup() {
        pageInfo = PageInfo(
            PageData(start = 1, display = 100, total = 1000)
        )
    }

    @Test
    fun `페이징 객체를 테스트 합니다`() {
        for (expectPage: Int in 1..15) {

            val firstPage = 1
            val nextPage = pageInfo.nextPage()

            pageInfo.pageInfo(
                start = if (expectPage == 1) firstPage else nextPage,
                total = 1_000
            )

            if (expectPage < 10) {
                assertEquals(true, pageInfo.isPaging())
            } else {
                assertEquals(false, pageInfo.isPaging())
            }
        }
    }
}