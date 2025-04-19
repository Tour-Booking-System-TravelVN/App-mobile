package com.tanh.tourbooking.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class TourUnitCalendar(
    val adultTourPrice: Double,
    val availableCapacity: Int,
    val babyTourPrice: Double,
    val childTourPrice: Double,
    val departureDate: LocalDate,
    val discount: Discount,
    val festival: Festival,
    val maximumCapacity: Int,
    val privateRoomPrice: Double,
    val returnDate: LocalDate,
    val toddlerTourPrice: Double,
    val tourUnitId: String
)