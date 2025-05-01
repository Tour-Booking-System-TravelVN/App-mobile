package com.tanh.tourbooking.presentation.explore

import com.tanh.tourbooking.domain.model.TourUnit
import java.time.LocalDate

data class ExploreUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val tourUnitList: List<TourUnit> = emptyList(),
    val startPrice: Double = 1000.0,
    val endPrice: Double = 1000000.0,
    val place: String = "",
    val startDate: String? = null
)
