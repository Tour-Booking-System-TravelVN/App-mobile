package com.tanh.tourbooking.data.model.response

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.tour.PaymentInformationDto

data class BookingResponse(
    @SerializedName("data")
    val data: PaymentInformationDto,
    val error: Int,
    val message: String
)