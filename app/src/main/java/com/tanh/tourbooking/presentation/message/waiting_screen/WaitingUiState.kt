package com.tanh.tourbooking.presentation.message.waiting_screen

data class WaitingUiState(
    val isLoading: Boolean = false,
    val waitingIds: List<Int> = emptyList()
)
