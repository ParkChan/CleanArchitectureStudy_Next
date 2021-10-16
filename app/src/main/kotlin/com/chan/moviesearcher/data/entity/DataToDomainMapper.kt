package com.chan.moviesearcher.data.entity

interface DataToDomainMapper<T> {
    fun mapToDto(): T
}