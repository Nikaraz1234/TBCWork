package com.example.tbcworks

import android.app.Application
import com.example.tbcworks.notification.NotificationChannels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
    }
}