package com.chan.movie.data.entity

interface DataToDomainMapper<T> {
    fun mapToDto(): T
}