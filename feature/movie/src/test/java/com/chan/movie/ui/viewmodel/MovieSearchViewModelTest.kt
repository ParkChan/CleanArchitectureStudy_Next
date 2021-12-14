package com.chan.movie.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.chan.movie.domain.data.MovieResult
import com.chan.movie.domain.usecase.MovieSearchUseCase
import com.chan.movie.ui.main.MovieSearchViewModel
import com.chan.movie.util.InstantExecutorExtension
import com.chan.movie.util.getOrAwaitValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MovieSearchViewModelTest {

    private val useCase: MovieSearchUseCase = FakeMovieSearchUseCase()
    private val savedStateHandle = SavedStateHandle()
    private val viewModel: MovieSearchViewModel by lazy {
        MovieSearchViewModel(savedStateHandle, useCase)
    }

    private class FakeMovieSearchUseCase : MovieSearchUseCase {
        override fun fetchMovies(start: Int, query: String): Flow<MovieResult> = flow {
            emit(MovieResult())
        }
    }

    @Test
    fun `영화 리스트를 불러옵니다`() = runBlocking {

        val mockMovieDto = MovieResult()
        viewModel.searchMovies("a")

        assertEquals(mockMovieDto.items, viewModel.movies.getOrAwaitValue())

    }
}