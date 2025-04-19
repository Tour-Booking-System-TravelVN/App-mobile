package com.tanh.tourbooking.data.model.response

import com.tanh.tourbooking.data.model.dto.tour.TourUnitCalendarDto

data class TourUnitCalendarResponse(
    val code: Int,
    val result: List<TourUnitCalendarDto>
)