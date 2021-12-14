package com.chan.movie.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val actor: String = "",
    val director: String = "",
    val image: String = "",
    val link: String = "",
    val pubDate: String = "",
    val subtitle: String = "",
    val title: String = "",
    val userRating: Double = 0.0
) : Parcelable