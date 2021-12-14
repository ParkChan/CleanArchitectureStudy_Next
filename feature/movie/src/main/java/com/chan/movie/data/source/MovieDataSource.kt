package com.chan.movie.data.source

import com.chan.movie.data.data.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    fun fetchMovies(start: Int, query: String): Flow<MovieResponse>
}