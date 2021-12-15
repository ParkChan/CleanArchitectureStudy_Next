package com.chan.movie.data.data

import com.chan.movie.data.ItemResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ItemResponseTest {

    private lateinit var gson: Gson

    @BeforeEach
    fun setup() {
        val gsonBuilder = GsonBuilder()
        gson = gsonBuilder.create()
    }

    @Test
    fun `영화 Item Json을 엔티티로 변환합니다`() {
        val response = gson.fromJson(ITEM_JSON, ItemResponse::class.java) ?: ItemResponse()

        assertEquals("보이나로비치", response.title)
        assertEquals("https://movie.naver.com/movie/bi/mi/basic.nhn?code=193591", response.link)
        assertEquals(0.00, response.userRating)
    }

    @Test
    fun `Json 엔티티를 Dto로 변환합니다`() {
        val dto = (gson.fromJson(ITEM_JSON, ItemResponse::class.java) ?: ItemResponse()).mapToDomain()

        assertEquals("보이나로비치", dto.title)
        assertEquals("https://movie.naver.com/movie/bi/mi/basic.nhn?code=193591", dto.link)
        assertEquals(0.00, dto.userRating)
    }

    companion object {
        private const val ITEM_JSON =
            """
                {
                    "title": "보이나로비치",
                    "link": "https://movie.naver.com/movie/bi/mi/basic.nhn?code=193591",
                    "image": "https://ssl.pstatic.net/imgmovie/mdi/mit110/1935/193591_P01_130234.JPG",
                    "subtitle": "Wojnarowicz",
                    "pubDate": "2020",
                    "director": "크리스 맥킴|",
                    "actor": "",
                    "userRating": "0.00"
                }
            """
    }
}