package com.tanh.tourbooking.data.model.request

data class CreatePaymentRequest(
    val bookingRequest: BookingRequest,
    val cancelUrl: String,
    val description: String,
    val price: Int,
    val productName: String,
    val returnUrl: String
)