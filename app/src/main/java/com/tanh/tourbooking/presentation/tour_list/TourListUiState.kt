package com.tanh.tourbooking.presentation.tour_list

import com.tanh.tourbooking.domain.model.TourUnit

data class TourListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val place: String? = null,
    val quantity: Int = 0,
    val list: List<TourUnit> = emptyList()
)
