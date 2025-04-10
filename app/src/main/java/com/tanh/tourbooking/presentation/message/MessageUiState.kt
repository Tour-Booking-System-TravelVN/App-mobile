package com.tanh.tourbooking.presentation.message

import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.model.Message

data class MessageUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val chatbox: ChatBox? = null
)