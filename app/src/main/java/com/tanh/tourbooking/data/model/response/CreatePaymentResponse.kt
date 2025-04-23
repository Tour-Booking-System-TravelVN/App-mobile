package com.tanh.tourbooking.data.model.response

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.payment.TransactionDetailDto

data class CreatePaymentResponse(
    @SerializedName("data")
    val data: TransactionDetailDto,
    val error: Int, //0 or -1
    val message: String
)