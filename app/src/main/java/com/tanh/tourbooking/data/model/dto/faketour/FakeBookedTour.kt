package com.tanh.tourbooking.data.model.dto.faketour

import com.tanh.tourbooking.util.TourStatus

data class FakeBookedTour(
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
    val status: TourStatus
)
