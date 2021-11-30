package com.chan.movie.domain.repository

import com.chan.movie.domain.dto.MovieDto

interface MovieSearchRepository {
    suspend fun fetchMovies(start: Int, query: String): MovieDto
}