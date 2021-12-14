package com.chan.movie.domain.usecase

import com.chan.movie.domain.data.MovieResult
import kotlinx.coroutines.flow.Flow

interface MovieSearchUseCase {
    fun fetchMovies(start: Int, query: String): Flow<MovieResult>
}