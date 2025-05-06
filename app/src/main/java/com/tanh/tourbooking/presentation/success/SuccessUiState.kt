package com.tanh.tourbooking.presentation.success

data class SuccessUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val bookingId: String? = null
)
