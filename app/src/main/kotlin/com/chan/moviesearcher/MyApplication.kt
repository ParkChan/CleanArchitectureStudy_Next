package com.chan.moviesearcher

import android.app.Application
import com.chan.movie.BuildConfig
import com.facebook.stetho.Stetho
import com.facebook.stetho.Stetho.newInitializerBuilder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
            )
        }
    }
}