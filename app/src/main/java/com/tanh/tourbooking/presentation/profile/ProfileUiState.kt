package com.tanh.tourbooking.presentation.profile

import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.util.Role

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val customer: Information.Customer? = null,
    val tourGuide: Information.TourGuide? = null,
    val role: Role = Role.NULL
)