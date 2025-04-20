package com.tanh.tourbooking.data.model.request

data class PaymentRequest(
    val productName: String,
    val description: String,
    val returnUrl: String,
    val cancelUrl: String,
    val price: Int,
    val bookingRequest: BookingRequest
)