package com.loskon.usercrud.app

import android.app.Application
import com.loskon.usercrud.BuildConfig
import com.loskon.usercrud.data.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        initializeKoin(this)
    }

    private fun initializeKoin(application: Application) {
        startKoin {
            androidContext(application)
            modules(listOf(networkModule))
        }
    }
}