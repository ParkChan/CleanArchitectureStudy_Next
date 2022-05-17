package com.chan.movie.data

import android.content.Context
import androidx.startup.Initializer
import com.chan.movie.BuildConfig
import timber.log.Timber

internal class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("Initializer >>> TimberInitializer is initialized.")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}