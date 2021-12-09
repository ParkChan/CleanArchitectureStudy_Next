package com.chan.movie.data

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val map : Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        for((key, value) in map){
            builder.addHeader(key, value)
        }
        return chain.proceed(
            builder.build()
        )
    }
}