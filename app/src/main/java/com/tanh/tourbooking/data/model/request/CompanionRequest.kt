package com.tanh.tourbooking.data.model.request

import com.google.gson.annotations.SerializedName

data class CompanionRequest(
    @SerializedName("c")
    val customer: CustomerRequest
)