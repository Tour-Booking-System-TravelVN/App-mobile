package com.tanh.tourbooking.domain.model

import java.security.Timestamp
import java.time.LocalDateTime

data class ChatBox(
    val participants: List<Int> = emptyList(),
    val lastTime: LocalDateTime,
    val message: String = "",
    val adminId: Int = 0,
    val chatId: String = "",
    val name: String = "",
    val uniqueBookingId: Int = 0
)

data class Message(
    val senderId: Int = 0,
    val text: String = "",
    val time: LocalDateTime,
    val senderName: String = ""
)