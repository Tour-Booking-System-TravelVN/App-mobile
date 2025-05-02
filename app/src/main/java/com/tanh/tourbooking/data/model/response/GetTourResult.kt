package com.tanh.tourbooking.data.model.response

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.tour.MyTourDto

data class GetTourResult(
    @SerializedName("content")
    val myTourDto: List<MyTourDto>,
    val page: Page
)