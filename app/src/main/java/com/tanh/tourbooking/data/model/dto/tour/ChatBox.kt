package com.tanh.tourbooking.data.model.dto.tour

import com.google.firebase.Timestamp

data class ChatBoxDto(
    val participants: List<Int> = emptyList(),
    val waitingId: List<Int> = emptyList(),
    val bannedId: List<Int> = emptyList(),
    val lastTimestamp: com.google.firebase.Timestamp = Timestamp.now(),
    val tourGuideId: List<Int> = emptyList(),
    val message: String = "",
    val chatId: String = "",
    val name: String = "",
    )

data class MessageDto(
    val senderId: Int = 0,
    val text: String = "",
    val timestamp: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
    val senderName: String = ""
)
