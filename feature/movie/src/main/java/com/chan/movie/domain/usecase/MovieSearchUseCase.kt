package com.chan.movie.domain.usecase

import com.chan.movie.domain.data.MovieData
import kotlinx.coroutines.flow.Flow

interface MovieSearchUseCase {
    fun fetchMovies(start: Int, query: String): Flow<MovieData>
}