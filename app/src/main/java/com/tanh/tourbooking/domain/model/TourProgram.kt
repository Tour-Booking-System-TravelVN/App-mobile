package com.tanh.tourbooking.domain.model

data class TourProgram(
    val day: Int,
    val description: String,
    val id: Int,
    val locations: String,
    val mealDescription: String
)