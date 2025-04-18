package com.tanh.tourbooking.data.model.response

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.tour.RatingDto

data class RatingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: List<RatingDto>
)