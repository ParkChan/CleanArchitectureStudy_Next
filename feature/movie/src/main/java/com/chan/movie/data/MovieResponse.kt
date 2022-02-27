package com.chan.movie.data

import com.chan.movie.domain.data.Item
import com.chan.movie.domain.data.MovieResult
import com.google.gson.annotations.SerializedName
import java.util.*

internal data class MovieResponse(
    @SerializedName("display")
    val display: Int = 0,
    @SerializedName("items")
    val itemRes: List<ItemResponse> = emptyList(),
    @SerializedName("lastBuildDate")
    val lastBuildDate: Date = Date(0),
    @SerializedName("start")
    val start: Int = 0,
    @SerializedName("total")
    val total: Int = 0
) : MapToDomain<MovieResult> {

    override fun mapToDomain(): MovieResult =
        MovieResult(
            display = display,
            items = itemRes.map {
                it.mapToDomain()
            },
            lastBuildDate = lastBuildDate,
            start = start,
            total = total,
        )
}

internal data class ItemResponse(
    @SerializedName("actor")
    val actor: String = "",
    @SerializedName("director")
    val director: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("pubDate")
    val pubDate: String = "",
    @SerializedName("subtitle")
    val subtitle: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("userRating")
    val userRating: Double = 0.0
) : MapToDomain<Item> {

    override fun mapToDomain(): Item =
        Item(
            actor = actor,
            director = director,
            image = image,
            link = link,
            pubDate = pubDate,
            subtitle = subtitle,
            title = title,
            userRating = userRating
        )
}
