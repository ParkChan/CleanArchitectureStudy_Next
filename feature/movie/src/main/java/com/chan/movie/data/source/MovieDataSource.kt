package com.chan.movie.data.source

import com.chan.movie.data.entity.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    suspend fun fetchMovies(start: Int, query: String): Flow<MovieResponse>
}