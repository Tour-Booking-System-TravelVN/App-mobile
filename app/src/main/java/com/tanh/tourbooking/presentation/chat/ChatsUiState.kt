package com.tanh.tourbooking.presentation.chat

import com.tanh.tourbooking.domain.model.ChatBox

data class ChatsUiState(
    val isLoading: Boolean? = false,
    val chats: List<ChatBox> = emptyList(),
    val error: String? = null
)
