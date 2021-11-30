package com.chan.movie.data.source

import com.chan.movie.data.entity.MovieResponse

interface MovieDataSource {
    suspend fun fetchMovies(start: Int, query: String): MovieResponse
}