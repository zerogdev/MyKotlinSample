package com.mysample.myapplication

import android.app.Application
import timber.log.Timber

class KotlinTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}