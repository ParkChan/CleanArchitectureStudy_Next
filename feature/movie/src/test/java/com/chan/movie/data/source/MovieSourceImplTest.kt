package com.chan.movie.data.source

import app.cash.turbine.test
import com.chan.movie.data.MovieResponse
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieSourceImplTest {

    private val movieApi: MovieApi = FakeMovieApi()
    private lateinit var movieDataSourceImpl: MovieDataSourceImpl

    private class FakeMovieApi : MovieApi {
        override suspend fun fetchMovies(start: Int, query: String, display: Int): MovieResponse =
            MovieResponse()
    }

    @BeforeEach
    fun setup() {
        movieDataSourceImpl = MovieDataSourceImpl(movieApi)
    }

    @Test
    fun `영화 리스트 가져오기 기능 테스트`() = runBlocking {
        val mockResponse = MovieResponse()

        movieDataSourceImpl.fetchMovies(1, "a").test {
            assertEquals(mockResponse, awaitItem())
            awaitComplete()
        }
    }
}