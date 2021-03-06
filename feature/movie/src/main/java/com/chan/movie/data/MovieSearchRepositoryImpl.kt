package com.chan.movie.data

import com.chan.movie.data.source.MovieDataSource
import com.chan.movie.domain.data.MovieResult
import com.chan.movie.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MovieSearchRepositoryImpl @Inject constructor(
    private val source: MovieDataSource,
) : MovieSearchRepository {
    override fun fetchMovies(start: Int, query: String): Flow<MovieResult> =
        source.fetchMovies(start = start, query = query).map(MovieResponse::mapToDomain)
}