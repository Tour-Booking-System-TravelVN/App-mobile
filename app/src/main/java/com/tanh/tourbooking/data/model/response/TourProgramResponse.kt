package com.tanh.tourbooking.data.model.response

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.tour.TourProgramDto

data class TourProgramResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("result")
    val result: List<TourProgramDto>
)