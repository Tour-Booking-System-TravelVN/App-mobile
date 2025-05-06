package com.tanh.tourbooking.presentation.my_tour

import com.tanh.tourbooking.domain.model.MyTour

data class MyTourUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val doneTours: List<MyTour> = emptyList(),
    val opwTours: List<MyTour> = emptyList(),
    val currentTour: MyTour? = null,
)
