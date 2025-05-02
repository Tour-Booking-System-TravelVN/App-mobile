package com.tanh.tourbooking.data.model.dto.tour

import com.google.gson.annotations.SerializedName
import com.tanh.tourbooking.data.model.dto.tour.CompanionCustomerSet
import com.tanh.tourbooking.domain.model.Information
import java.time.LocalDateTime

data class MyTourDto(
    val adultNumber: Int,
    val babyNumber: Int,
    val bookingDate: String,
    val bookingId: String,
    val c: CustomerDto?,
    val childNumber: Int,
    val companionCustomerSet: List<CompanionCustomerSet>,
    val note: String?,
    @SerializedName("payment_id")
    val paymentId: String?,
    val privateRoomNumber: Int?,
    val status: String,
    val toddlerNumber: Int,
    val totalAmount: Double,
    val tourUnit: TourUnitDto
)