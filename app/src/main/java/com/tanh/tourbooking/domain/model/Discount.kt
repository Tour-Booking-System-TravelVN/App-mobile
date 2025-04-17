package com.tanh.tourbooking.domain.model

data class Discount(
    val discountName: String,
    val discountUnit: String,
    val discountValue: Double,
    val id: Int
)