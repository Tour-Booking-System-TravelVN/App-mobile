package com.tanh.tourbooking.data.model.response

import com.tanh.tourbooking.data.model.dto.tour.CustomerDto
import com.tanh.tourbooking.data.model.dto.tour.TourGuideDto

data class UserInformationResult(
    val c: CustomerDto?,
    val email: String,
    val status: String,
    val tourGuide: TourGuideDto?,
    val username: String
)