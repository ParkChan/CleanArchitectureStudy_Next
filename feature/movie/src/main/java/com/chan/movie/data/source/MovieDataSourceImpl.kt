package com.chan.movie.data.source

import com.chan.movie.data.entity.MovieResponse
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieDataSource {
    override suspend fun fetchMovies(start: Int, query: String): MovieResponse =
        movieApi.fetchMovies(start, query)
}