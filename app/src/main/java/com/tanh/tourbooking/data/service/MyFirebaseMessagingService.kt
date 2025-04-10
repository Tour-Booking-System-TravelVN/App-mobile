package com.tanh.tourbooking.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tanh.tourbooking.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "New Token: $token")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM", "Received FCM Message: ${message.data}")

        val title = message.notification?.title ?: message.data["title"] ?: "Tin nhắn mới"
        val body = message.notification?.body ?: message.data["body"] ?: "Bạn có tin nhắn mới!"

        showNotification(title, body)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String, message: String) {

        Log.d("FCM", "notification run")

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val existingChannel = notificationManager.getNotificationChannel("message")
        if(existingChannel == null) {
            val channel = NotificationChannel(
                "message",
                "send message",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val bitmapIcon = BitmapFactory.decodeResource(resources, R.drawable.group)

        val notification = NotificationCompat.Builder(this, "message")
            .setLargeIcon(bitmapIcon)
            .setSmallIcon(R.drawable.new_message)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val id = System.currentTimeMillis().toInt()
        notificationManager.notify(id, notification.build())
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

}