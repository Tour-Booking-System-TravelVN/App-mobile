package com.tanh.tourbooking.domain.model

import java.security.Timestamp
import java.time.LocalDateTime

data class ChatBox(
    val participants: List<Int> = emptyList(),
    val waitingId: List<Int> = emptyList(),
    val bannedId: List<Int> = emptyList(),
    val lastTime: LocalDateTime,
    val tourGuideId: List<Int> = emptyList(),
    val message: String = "",
    val chatId: String = "",
    val name: String = "",
)

data class Message(
    val senderId: Int = 0,
    val text: String = "",
    val time: LocalDateTime,
    val senderName: String = ""
)