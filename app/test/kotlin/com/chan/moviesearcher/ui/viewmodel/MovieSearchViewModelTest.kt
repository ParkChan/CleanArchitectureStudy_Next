package com.chan.moviesearcher.ui.viewmodel

import com.chan.moviesearcher.domain.dto.ItemDto
import com.chan.moviesearcher.domain.usecase.MovieSearchUseCaseImpl
import com.chan.moviesearcher.ui.MovieSearchViewModel
import com.chan.moviesearcher.util.InstantExecutorExtension
import com.chan.moviesearcher.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MovieSearchViewModelTest {

    private val useCase: MovieSearchUseCaseImpl = mockk(relaxed = true)
    private lateinit var viewModel: MovieSearchViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MovieSearchViewModel(useCase)
    }

    @Test
    fun `영화 리스트를 불러옵니다`() = runBlocking {

        val mockRes: List<ItemDto> = emptyList()

        val page = 1
        val query = "a"

        coEvery {
            useCase.request(
                page,
                query
            ).getOrNull()?.items
        } returns mockRes

        viewModel.getMovieList(
            query
        )

        assertEquals(
            mockRes,
            viewModel.movies.getOrAwaitValue()
        )
    }
}