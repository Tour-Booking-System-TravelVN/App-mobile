package com.tanh.tourbooking.presentation.home

import com.tanh.tourbooking.domain.model.Information

data class HomeUiState(
    val isLoading: Boolean = false,
    val information: Information.Customer? = null
)
