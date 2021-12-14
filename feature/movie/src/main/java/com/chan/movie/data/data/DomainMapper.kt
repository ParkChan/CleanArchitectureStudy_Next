package com.chan.movie.data.data

interface DomainMapper<T> {
    fun mapToDto(): T
}