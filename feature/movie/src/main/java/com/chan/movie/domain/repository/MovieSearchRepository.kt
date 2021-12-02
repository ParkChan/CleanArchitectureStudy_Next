package com.chan.movie.domain.repository

import com.chan.movie.domain.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieSearchRepository {
    suspend fun fetchMovies(start: Int, query: String): Flow<MovieDto>
}