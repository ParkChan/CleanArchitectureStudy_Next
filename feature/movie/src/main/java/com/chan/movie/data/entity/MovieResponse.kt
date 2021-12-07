package com.chan.movie.data.entity

import com.chan.movie.domain.data.MovieData
import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieResponse(
    @SerializedName("display")
    val display: Int = 0,
    @SerializedName("items")
    val items: List<Item> = emptyList(),
    @SerializedName("lastBuildDate")
    val lastBuildDate: Date = Date(0),
    @SerializedName("start")
    val start: Int = 0,
    @SerializedName("total")
    val total: Int = 0
) : DataToDomainMapper<MovieData> {

    override fun mapToDto(): MovieData =
        MovieData(
            display = display,
            items = items.map {
                it.mapToDto()
            },
            lastBuildDate = lastBuildDate,
            start = start,
            total = total,
        )
}
