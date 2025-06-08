package com.tanh.tourbooking

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.datastore.dataStore
import dagger.hilt.android.HiltAndroidApp
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

@HiltAndroidApp
class TourBookingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ZaloPaySDK.init(2553, Environment.SANDBOX)
        val notificationChannel = NotificationChannel(
            "message",
            "Send message",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

}