package com.tanh.tourbooking.data.model.response

import com.tanh.tourbooking.data.model.dto.tour.CustomerDto

data class UpdateInfoResponse(
    val administrator: Any,
    val c: CustomerDto,
    val email: String?,
    val status: String,
    val tourGuide: Any,
    val tourOperator: Any,
    val username: String
)