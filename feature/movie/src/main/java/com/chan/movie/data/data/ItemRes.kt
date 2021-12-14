package com.chan.movie.data.data

import com.chan.movie.domain.data.Item
import com.google.gson.annotations.SerializedName

data class ItemRes(
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
) : DomainMapper<Item> {

    override fun mapToDto(): Item =
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