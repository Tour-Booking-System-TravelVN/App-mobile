package com.tanh.tourbooking.data.model.dto.faketour

data class FakeTourProgram(
    val name: String,
    val description: String,
    val rated: Int,
    val totalRate: Int,
    val price: Double,
    val image: List<String>,
    val vehicle: String,
    val duration: String,
    val startDestination: String,
    val schedules: List<FakeTour>,
    val maxParticipant: Int,
    val fakeTourGuide: FakeTourGuide,
)
