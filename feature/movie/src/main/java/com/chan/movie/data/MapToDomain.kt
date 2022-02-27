package com.chan.movie.data

internal interface MapToDomain<T> {
    fun mapToDomain(): T
}