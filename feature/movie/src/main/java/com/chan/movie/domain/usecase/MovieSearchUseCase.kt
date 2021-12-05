package com.chan.movie.domain.usecase

import com.chan.movie.domain.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieSearchUseCase {
    fun fetchMovies(start: Int, query: String): Flow<MovieDto>
}