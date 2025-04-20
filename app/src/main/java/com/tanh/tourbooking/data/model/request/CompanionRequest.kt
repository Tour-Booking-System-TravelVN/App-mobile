package com.tanh.tourbooking.data.model.request

data class CompanionRequest(
    val firstname: String,
    val lastname: String,
    val dob: String,
    val gender: Boolean
)