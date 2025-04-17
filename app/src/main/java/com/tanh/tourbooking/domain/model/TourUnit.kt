package com.tanh.tourbooking.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class TourUnit(
    val adultTourCost: Double,
    val adultTourPrice: Double,
    val availableCapacity: Int,
    val babyTourCost: Double,
    val babyTourPrice: Double,
    val childTourCost: Double,
    val childTourPrice: Double,
    val createdTime: LocalDateTime?,
    val departureDate: LocalDate?,
    val discount: Discount?,
    val festival: Festival?,
    val lastUpdatedOperator: Int?,
    val lastUpdatedTime: LocalDateTime?,
    val maximumCapacity: Int,
    val privateRoomPrice: Double,
    val returnDate: LocalDate?,
    val toddlerTourCost: Double,
    val toddlerTourPrice: Double,
    val totalAdditionalCost: Double,
    val tour: Tour,
    val tourOperator: Int?,
    val tourUnitId: String
)