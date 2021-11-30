package com.chan.movie.domain.usecase

import com.chan.movie.domain.dto.MovieDto
import com.chan.movie.domain.repository.MovieSearchRepository
import javax.inject.Inject

class MovieSearchUseCaseImpl @Inject constructor(
    private val repository: MovieSearchRepository
) : MovieSearchUseCase {
    override suspend fun request(start: Int, query: String): Result<MovieDto> =
        runCatching {
            repository.fetchMovies(start, query)
        }
}