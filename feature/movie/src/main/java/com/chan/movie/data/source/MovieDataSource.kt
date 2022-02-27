package com.chan.movie.data.source

import com.chan.movie.data.MovieResponse
import kotlinx.coroutines.flow.Flow

internal interface MovieDataSource {
    fun fetchMovies(start: Int, query: String): Flow<MovieResponse>
}