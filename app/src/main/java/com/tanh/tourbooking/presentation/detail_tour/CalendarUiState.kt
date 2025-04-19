package com.tanh.tourbooking.presentation.detail_tour

import com.tanh.tourbooking.domain.model.TourUnitCalendar
import com.tanh.tourbooking.util.Month

data class CalendarUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val calendar: List<Pair<Int, TourUnitCalendar>> = emptyList(),
    val months: List<Pair<Int, Month>> = emptyList()
)
