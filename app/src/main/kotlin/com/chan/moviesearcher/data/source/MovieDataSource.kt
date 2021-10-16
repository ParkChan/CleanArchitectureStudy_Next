package com.chan.moviesearcher.data.source

import com.chan.moviesearcher.data.entity.MovieResponse

interface MovieDataSource {
    suspend fun fetchMovies(start: Int, query: String): MovieResponse
}