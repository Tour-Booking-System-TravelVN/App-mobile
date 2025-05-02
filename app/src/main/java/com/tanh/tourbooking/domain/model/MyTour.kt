package com.tanh.tourbooking.domain.model

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.tour.CompanionCustomerSet
import java.time.LocalDateTime

data class MyTour(
    val adultNumber: Int,
    val babyNumber: Int,
    val bookingDate: LocalDateTime,
    val bookingId: String,
    val customer: Information.Customer?,
    val childNumber: Int,
    val companionCustomerSet: List<CompanionCustomerSet>,
    val note: String?,
    val paymentId: String?,
    val privateRoomNumber: Int?,
    val status: String,
    val toddlerNumber: Int,
    val totalAmount: Double,
    val tourUnit: TourUnit
)