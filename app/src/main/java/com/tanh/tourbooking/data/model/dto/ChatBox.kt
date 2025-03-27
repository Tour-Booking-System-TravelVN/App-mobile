package com.tanh.tourbooking.data.model.dto

import com.google.firebase.Timestamp

data class ChatBoxDto(
    val participants: List<Int> = emptyList(),
    val lastTimestamp: com.google.firebase.Timestamp = Timestamp.now(),
    val message: String = "",
    val adminId: Int = 0,
    val chatId: String = "",
    val name: String = "",
    val uniqueBookingId: Int = 0,
    val participantTokens: List<Map<String, String>> = emptyList()
)

data class MessageDto(
    val senderId: Int = 0,
    val text: String = "",
    val timestamp: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
    val senderName: String = ""
)
