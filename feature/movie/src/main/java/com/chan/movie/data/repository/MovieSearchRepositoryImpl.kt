package com.chan.movie.data.repository

import com.chan.movie.data.source.MovieDataSource
import com.chan.movie.domain.dto.MovieDto
import com.chan.movie.domain.repository.MovieSearchRepository
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val source: MovieDataSource,
) : MovieSearchRepository {
    override suspend fun fetchMovies(start: Int, query: String): MovieDto =
        source.fetchMovies(start = start, query = query).mapToDto()
}