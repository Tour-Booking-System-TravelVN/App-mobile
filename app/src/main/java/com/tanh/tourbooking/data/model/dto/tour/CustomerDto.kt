package com.tanh.tourbooking.data.model.dto.tour

data class CustomerDto(
    val address: String,
    val citizenId: String,
    val dateOfBirth: String,
    val firstname: String,
    val gender: Boolean,
    val id: Int,
    val lastname: String,
    val nationality: String,
    val note: String?,
    val passport: String,
    val phoneNumber: String
)