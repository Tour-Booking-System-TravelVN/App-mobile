package com.tanh.tourbooking.data.model.dto.tour

data class TourProgram(
    val name: String,
    val description: String,
    val rated: Int,
    val totalRate: Int,
    val price: Double,
    val image: List<String>,
    val vehicle: String,
    val duration: String,
    val startDestination: String,
    val schedules: List<Tour>,
    val maxParticipant: Int,
    val tourGuide: TourGuide,
)
