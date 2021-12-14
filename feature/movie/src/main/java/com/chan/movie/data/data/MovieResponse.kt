package com.chan.movie.data.data

import com.chan.movie.domain.data.MovieResult
import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieResponse(
    @SerializedName("display")
    val display: Int = 0,
    @SerializedName("items")
    val itemRes: List<ItemRes> = emptyList(),
    @SerializedName("lastBuildDate")
    val lastBuildDate: Date = Date(0),
    @SerializedName("start")
    val start: Int = 0,
    @SerializedName("total")
    val total: Int = 0
) : DomainMapper<MovieResult> {

    override fun mapToDto(): MovieResult =
        MovieResult(
            display = display,
            items = itemRes.map {
                it.mapToDto()
            },
            lastBuildDate = lastBuildDate,
            start = start,
            total = total,
        )
}
