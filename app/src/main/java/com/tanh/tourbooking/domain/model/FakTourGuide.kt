package com.tanh.tourbooking.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FakTourGuide(
    val id: Int,
    val name: String,
    val email: String
)

@Serializable
data class FakCustomer(
    val id: Int,
    val username: String,
    val password: String
)
