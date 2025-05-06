package com.tanh.tourbooking.data.model.response

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.payment.ConfirmedDataDto

data class ConfirmPaymentResponse(
    @SerializedName("data")
    val confirmedData: ConfirmedDataDto,
    val error: Int,
    val message: String
)