package com.devfutech.paradisonesia

import android.app.Application
import com.devfutech.paradisonesia.external.CrashReportingTree
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Paradisonesia :Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree())
        if (BuildConfig.DEBUG)
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false)
        else
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
    }
}