package com.tanh.tourbooking.presentation.detail_tour

import com.tanh.tourbooking.domain.model.Rating
import com.tanh.tourbooking.domain.model.TourProgram
import com.tanh.tourbooking.domain.model.TourUnit

data class DetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val ratingError: String? = null,
    val tourProgramError: String? = null,

    val tourUnit: TourUnit? = null,
    val ratings: List<Rating> = emptyList(),
    val tourProgram: List<TourProgram> = emptyList()
)
