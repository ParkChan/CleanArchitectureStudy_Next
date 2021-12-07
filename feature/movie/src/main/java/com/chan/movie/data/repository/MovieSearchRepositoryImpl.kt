package com.chan.movie.data.repository

import com.chan.movie.data.entity.MovieResponse
import com.chan.movie.data.source.MovieDataSource
import com.chan.movie.domain.data.MovieData
import com.chan.movie.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val source: MovieDataSource,
) : MovieSearchRepository {
    override fun fetchMovies(start: Int, query: String): Flow<MovieData> =
        source.fetchMovies(start = start, query = query).map(MovieResponse::mapToDto)
}