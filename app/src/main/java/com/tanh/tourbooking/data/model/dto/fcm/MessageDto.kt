package com.tanh.tourbooking.data.model.dto.fcm

import com.google.gson.annotations.SerializedName

data class Message(
    val topic: String,
    val notification: Notification,
    val android: AndroidConfig,
    val apns: Apns
)

data class AndroidConfig(
    val priority: String
)

data class Apns(
    val payload: Payload
)

data class Payload(
    val aps: Aps
)

data class Aps(
    @SerializedName("content-available") val contentAvailable: Int
)

