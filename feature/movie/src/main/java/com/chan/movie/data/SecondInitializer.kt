package com.chan.movie.data

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

internal class SecondInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Timber.d("Initializer >>> SecondInitializer is initialized!")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java)
    }

}