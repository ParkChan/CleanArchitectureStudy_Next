package com.chan.movie.data.entity

import com.chan.movie.domain.dto.ItemDto
import com.google.gson.annotations.SerializedName

data class Item(
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
) : DataToDomainMapper<ItemDto> {

    override fun mapToDto(): ItemDto =
        ItemDto(
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