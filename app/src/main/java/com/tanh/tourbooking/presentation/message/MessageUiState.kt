package com.tanh.tourbooking.presentation.message

import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.model.Message
import com.tanh.tourbooking.util.Role

data class MessageUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val chatbox: ChatBox? = null,

    val customer: Information.Customer? = null,
    val tourguide: Information.TourGuide? = null,
    val role: Role = Role.NULL
)