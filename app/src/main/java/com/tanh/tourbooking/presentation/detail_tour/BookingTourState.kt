package com.tanh.tourbooking.presentation.detail_tour

import com.tanh.tourbooking.data.serializer.LocalDateSerializer
import com.tanh.tourbooking.domain.model.Discount
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class BookingTourState(
    val tourName: String = "",
    val tourUnitId: String = "",
    val adultNumber: Int = 1,
    val childNumber: Int = 0,
    val toddleNumber: Int = 0,
    val babyNumber: Int = 0,
    val adultPrice: Double = 0.0,
    val childPrice: Double = 0.0,
    val toddlePrice: Double = 0.0,
    val babyPrice: Double = 0.0,
    val totalPrice: Double = 0.0,
    @Serializable(with = LocalDateSerializer::class)
    val departureDate: LocalDate? = null,
    val discount: Discount? = null
)
