package com.chan.movie.data.entity

import com.chan.movie.domain.dto.MovieDto
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
) : DataToDomainMapper<MovieDto> {

    override fun mapToDto(): MovieDto =
        MovieDto(
            display = display,
            items = items.map {
                it.mapToDto()
            },
            lastBuildDate = lastBuildDate,
            start = start,
            total = total,
        )
}
