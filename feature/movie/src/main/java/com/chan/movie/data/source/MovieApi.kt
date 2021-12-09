package com.chan.movie.data.source

import com.chan.movie.data.entity.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/v1/search/movie.json")
    suspend fun fetchMovies(
        @Query("start") start: Int,
        @Query("query") query: String,
        @Query("display") display: Int = DEFAULT_DISPLAY_COUNT
    ): MovieResponse

    companion object {
        private const val DEFAULT_DISPLAY_COUNT = 20
    }
}