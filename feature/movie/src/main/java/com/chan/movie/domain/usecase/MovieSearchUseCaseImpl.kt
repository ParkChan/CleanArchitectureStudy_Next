package com.chan.movie.domain.usecase

import com.chan.movie.domain.data.MovieData
import com.chan.movie.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieSearchUseCaseImpl @Inject constructor(
    private val repository: MovieSearchRepository
) : MovieSearchUseCase {
    override fun fetchMovies(start: Int, query: String): Flow<MovieData> =
        repository.fetchMovies(
            start = start,
            query = query
        )
}