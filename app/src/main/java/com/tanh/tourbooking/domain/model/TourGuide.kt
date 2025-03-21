package com.tanh.tourbooking.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TourGuide(
    val id: Int,
    val name: String,
    val email: String
)

@Serializable
data class Customer(
    val id: Int,
    val username: String,
    val password: String
)
