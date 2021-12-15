package com.chan.movie.data.source

import com.chan.movie.data.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieDataSource {
    override fun fetchMovies(start: Int, query: String): Flow<MovieResponse> =
        flow {
            emit(
                movieApi.fetchMovies(
                    start = start,
                    query = query
                )
            )
        }.flowOn(Dispatchers.IO)

}