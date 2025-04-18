package com.tanh.tourbooking.domain.model

import com.tanh.tourbooking.data.serializer.LocalDateSerializer
import com.tanh.tourbooking.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class TourUnit(
    val adultTourCost: Double,
    val adultTourPrice: Double,
    val availableCapacity: Int,
    val babyTourCost: Double,
    val babyTourPrice: Double,
    val childTourCost: Double,
    val childTourPrice: Double,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdTime: LocalDateTime?,
    @Serializable(with = LocalDateSerializer::class)
    val departureDate: LocalDate?,
    val discount: Discount?,
    val festival: Festival?,
    val lastUpdatedOperator: Int?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val lastUpdatedTime: LocalDateTime?,
    val maximumCapacity: Int,
    val privateRoomPrice: Double,
    @Serializable(with = LocalDateSerializer::class)
    val returnDate: LocalDate?,
    val toddlerTourCost: Double,
    val toddlerTourPrice: Double,
    val totalAdditionalCost: Double,
    val tour: Tour,
    val tourOperator: Int?,
    val tourUnitId: String
)