package com.tanh.tourbooking.domain.repository.firestore

interface NotificationHandler {
    fun sendNotificationToUser(token: String, title: String, message: String)
    fun subscribeTopic(topic: String)
    suspend fun sendNotificationByTopic(topic: String, title: String, body: String)
    suspend fun unsubscribeTopic(topic: String)
}