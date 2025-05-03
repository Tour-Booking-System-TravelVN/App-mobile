package com.tanh.tourbooking.presentation.category

import com.tanh.tourbooking.domain.model.TourUnit

data class CategoryUiState(
    val isLoading: Boolean = false,
    val list: List<TourUnit> = emptyList(),
)
