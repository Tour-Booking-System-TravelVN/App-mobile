package com.tanh.tourbooking.data.model.response

import com.google.gson.annotations.SerializedName

data class UserInformationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: UserInformationResult
)