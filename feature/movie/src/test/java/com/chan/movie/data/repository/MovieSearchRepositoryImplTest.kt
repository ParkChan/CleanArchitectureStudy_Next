package com.chan.movie.data.repository

import app.cash.turbine.test
import com.chan.movie.data.data.MovieResponse
import com.chan.movie.data.source.MovieDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieSearchRepositoryImplTest {

    private val movieDataSource = FakeMovieDataSource()
    private lateinit var repositoryImpl: MovieSearchRepositoryImpl

    private class FakeMovieDataSource : MovieDataSource {
        override fun fetchMovies(start: Int, query: String): Flow<MovieResponse> = flow {
            emit(MovieResponse())
        }
    }

    @BeforeEach
    fun setup() {
        repositoryImpl = MovieSearchRepositoryImpl(movieDataSource)
    }

    @Test
    fun `영화 리스트 가져오기 기능 테스트`() = runBlocking {

        val mockMovieResponse = MovieResponse()

        repositoryImpl.fetchMovies(start = 1, query = "a").test {
            assertEquals(mockMovieResponse.mapToDto(), awaitItem())
            awaitComplete()
        }
    }
}