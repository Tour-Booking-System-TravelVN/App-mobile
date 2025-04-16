package com.tanh.tourbooking.data.model.response

import com.tanh.tourbooking.data.model.dto.tour.TourUnitDto

data class TourUnitByPlaceResponse(
    val code: Int,
    val message: String,
    val result: List<TourUnitDto>
)