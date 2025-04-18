package com.tanh.tourbooking.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Discount(
    val discountName: String,
    val discountUnit: String,
    val discountValue: Double,
    val id: Int
)