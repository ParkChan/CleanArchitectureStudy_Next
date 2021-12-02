package com.chan.movie.domain.usecase

import com.chan.movie.domain.dto.MovieDto
import com.chan.movie.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieSearchUseCaseImpl @Inject constructor(
    private val repository: MovieSearchRepository
) : MovieSearchUseCase {
    override suspend fun fetchMovies(start: Int, query: String): Flow<MovieDto> =
        repository.fetchMovies(
            start = start,
            query = query
        )
}