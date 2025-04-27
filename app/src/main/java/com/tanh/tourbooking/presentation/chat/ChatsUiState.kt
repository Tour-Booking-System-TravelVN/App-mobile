package com.tanh.tourbooking.presentation.chat

import com.tanh.tourbooking.domain.model.ChatBox
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.util.Role

data class ChatsUiState(
    val isLoading: Boolean? = false,
    val customer: Information.Customer? = null,
    val tourguide: Information.TourGuide? = null,
    val role: Role = Role.NULL,
    val error: String? = null
)
