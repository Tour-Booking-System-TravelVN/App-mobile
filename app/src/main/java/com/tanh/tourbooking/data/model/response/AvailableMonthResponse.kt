package com.tanh.tourbooking.data.model.response

import com.tanh.tourbooking.data.model.dto.tour.AvailableMonthDto

data class AvailableMonthResponse(
    val code: Int,
    val result: List<AvailableMonthDto>
)