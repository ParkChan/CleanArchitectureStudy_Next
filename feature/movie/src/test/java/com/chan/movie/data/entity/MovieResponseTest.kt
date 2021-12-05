package com.chan.movie.data.entity

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File


class MovieResponseTest {

    private lateinit var gson: Gson
    private val NAVER_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"

    @BeforeEach
    fun setup() {
        gson = GsonBuilder()
            .setDateFormat(NAVER_DATE_FORMAT)
            .setPrettyPrinting().create()
    }

    @Test
    fun `json 파일을 읽어와서 엔티티로 변환합니다`() {
        val json = javaClass.classLoader
            ?.getResourceAsStream("api-response/1.json")
            ?.bufferedReader()
            ?.use(BufferedReader::readText)
            ?: ""

        val response = gson.fromJson(json, MovieResponse::class.java) ?: MovieResponse()

        assertEquals(88, response.total)
        assertEquals(2, response.start)
        assertEquals(10, response.display)
    }

    @Test
    fun `Json 엔티티를 Dto로 변환합니다`() {
        val json = File("src/test/resources/api-response/1.json").readText()
        val dto = (gson.fromJson(json, MovieResponse::class.java) ?: MovieResponse()).mapToDto()

        assertEquals(88, dto.total)
        assertEquals(2, dto.start)
        assertEquals(10, dto.display)
    }
}