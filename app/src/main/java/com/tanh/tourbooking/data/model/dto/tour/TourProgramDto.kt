package com.tanh.tourbooking.data.model.dto.tour

import com.google.gson.annotations.SerializedName

data class TourProgramDto(
    val day: Int,
    @SerializedName("desciption")
    val description: String,
    val id: Int,
    val locations: String,
    val mealDescription: String
)