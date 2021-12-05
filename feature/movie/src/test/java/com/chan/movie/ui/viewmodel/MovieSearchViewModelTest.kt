package com.chan.movie.ui.viewmodel

import com.chan.movie.domain.dto.MovieDto
import com.chan.movie.domain.usecase.MovieSearchUseCase
import com.chan.movie.ui.main.MovieSearchViewModel
import com.chan.movie.util.InstantExecutorExtension
import com.chan.movie.util.getOrAwaitValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MovieSearchViewModelTest {

    private val useCase: MovieSearchUseCase = FakeMovieSearchUseCase()
    private lateinit var viewModel: MovieSearchViewModel

    private class FakeMovieSearchUseCase : MovieSearchUseCase {
        override fun fetchMovies(start: Int, query: String): Flow<MovieDto> = flow {
            emit(MovieDto())
        }
    }

    @BeforeEach
    fun setUp() {
        viewModel = MovieSearchViewModel(useCase)
    }

    @Test
    fun `영화 리스트를 불러옵니다`() = runBlocking {

        val mockMovieDto = MovieDto()
        viewModel.searchMovies("a")

        assertEquals(mockMovieDto.items, viewModel.movies.getOrAwaitValue())

    }
}