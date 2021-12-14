package com.chan.movie.domain.repository

import com.chan.movie.domain.data.MovieResult
import kotlinx.coroutines.flow.Flow

interface MovieSearchRepository {
    fun fetchMovies(start: Int, query: String): Flow<MovieResult>
}