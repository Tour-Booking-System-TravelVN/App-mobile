package com.tanh.tourbooking.data.repository.firestore

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.tanh.tourbooking.data.model.dto.fcm.AndroidConfig
import com.tanh.tourbooking.data.model.dto.fcm.Apns
import com.tanh.tourbooking.data.model.dto.fcm.Aps
import com.tanh.tourbooking.data.model.dto.fcm.FCMMessage
import com.tanh.tourbooking.data.model.dto.fcm.Message
import com.tanh.tourbooking.data.model.dto.fcm.Notification
import com.tanh.tourbooking.data.model.dto.fcm.Payload
import com.tanh.tourbooking.data.networking.api.FCMApi
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.firestore.NotificationHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NotificationHandlerImpl @Inject constructor(
    private val api: FCMApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val firebaseMessaging: FirebaseMessaging
) : NotificationHandler {

    override fun subscribeTopic(topic: String) {
        firebaseMessaging.subscribeToTopic(topic).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Log.d("FCM", "Subscribed to topic: $topic")
            } else {
                Log.e("FCM", "Error subscribing to topic: $topic", task.exception)
            }
        }
    }

    override suspend fun unsubscribeTopic(topic: String) {
        withContext(dispatcher) {
            firebaseMessaging.unsubscribeFromTopic(topic).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.d("FCM", "Unsubscribed to topic: $topic")
                } else {
                    Log.e("FCM", "Error unsubscribing to topic: $topic", task.exception)
                }
            }
        }
    }

    override suspend fun sendNotificationByTopic(topic: String, title: String, body: String) {
        withContext(dispatcher) {
            val message = FCMMessage(
                Message(
                    notification = Notification(
                        title = title,
                        body = body
                    ),
                    topic = topic,
                    android = AndroidConfig(
                        priority = "high"
                    ),
                    apns = Apns(
                        payload = Payload(
                            aps = Aps(
                                contentAvailable = 1
                            )
                        )
                    )
                )
            )

            api.sendTopic(message).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("FCM", "Notification sent successfully: ${response.code()}")
                    Log.d("FCM", response.errorBody()?.string() ?: "No error")
                    Log.d("FCM", response.body().toString() ?: "No error")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("FCM", "Error sending notification", t)
                }
            })
        }
    }

    override fun sendNotificationToUser(token: String, title: String, message: String) {
//        Log.d("FCM", "User token: $token")
//
//        val notification = FCMNotification(
//            message = Message(
//                notification = Notification(
//                    title = title,
//                    body = message
//                ),
//                token = token,
//                android = AndroidConfig(
//                    priority = "high"
//                ),
//                apns = Apns(
//                    payload = Payload(
//                        aps = Aps(
//                            contentAvailable = 1
//                        )
//                    )
//                )
//            )
//        )
//
//        api.sendNotification(notification).enqueue(object : Callback<FCMNotification> {
//            override fun onResponse(
//                call: Call<FCMNotification>,
//                response: Response<FCMNotification>
//            ) {
//                Log.d("FCM", "Notification sent successfully: ${response.code()}")
//                Log.d("FCM", "AccessToken: ${AccessToken.getAccessToken()}")
//            }
//
//            override fun onFailure(call: Call<FCMNotification>, t: Throwable) {
//                Log.e("FCM", "Error sending notification", t)
//            }
//        })
    }
}