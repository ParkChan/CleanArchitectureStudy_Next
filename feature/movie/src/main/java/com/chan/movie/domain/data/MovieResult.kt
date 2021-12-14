package com.chan.movie.domain.data

import java.util.*

data class MovieResult(
    val display: Int = 0,
    val items: List<Item> = emptyList(),
    val lastBuildDate: Date = Date(0),
    val start: Int = 0,
    val total: Int = 0
)