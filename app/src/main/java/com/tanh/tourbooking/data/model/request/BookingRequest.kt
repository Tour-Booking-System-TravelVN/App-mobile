package com.tanh.tourbooking.data.model.request

import com.google.gson.annotations.SerializedName

data class BookingRequest(
    @SerializedName("c")
    val customer: CustomerRequest,
    val tourUnitId: String,
    val babyNumber: Int,
    val toddlerNumber: Int,
    val childNumber: Int,
    val adultNumber: Int,
    val privateRoomNumber: Int,
    val note: String,
    val totalAmount: Int,
    val companions: List<CompanionRequest>
)