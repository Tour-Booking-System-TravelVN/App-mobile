package com.tanh.tourbooking.presentation.test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.tanh.tourbooking.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TestScreen(
    viewModel: TestViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.onEvent()
            }
        ) {
            Text(text = "Send notification")
        }
        Button(
            onClick = {
                viewModel.onEvent2()
            }
        ) {
            Text(text = "subscribe topic")
        }
        Button(
            onClick = {
                val notificationManager =
                    context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val existingChannel = notificationManager.getNotificationChannel("message")
                if (existingChannel == null) {
                    val channel = NotificationChannel(
                        "message",
                        "send message",
                        NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        enableLights(true)
                        enableVibration(true)
                        vibrationPattern = longArrayOf(100, 200, 300, 400)
                    }
                    notificationManager.createNotificationChannel(channel)
                }

                val notification = NotificationCompat.Builder(context, "message")
                    .setSmallIcon(R.drawable.new_message)
                    .setContentTitle("hellooo")
                    .setContentText("Hello")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true)

                val id = System.currentTimeMillis().toInt()
                notificationManager.notify(id, notification.build())
            }
        ) {
            Text("Show notification")
        }
    }

}