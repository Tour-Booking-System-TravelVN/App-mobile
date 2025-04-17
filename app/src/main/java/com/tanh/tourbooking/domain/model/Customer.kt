package com.tanh.tourbooking.domain.model

sealed class Information() {

    data class Customer(
        val address: String?,
        val citizenId: String?,
        val dateOfBirth: String,
        val firstname: String,
        val gender: Boolean,
        val id: Int,
        val lastname: String,
        val nationality: String?,
        val note: String?,
        val passport: String?,
        val phoneNumber: String?
    ): Information()

    data class TourGuide(
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
    ): Information()

}