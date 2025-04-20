package com.tanh.tourbooking.data.model.request

data class CustomerRequest(
    val firstname: String,
    val lastname: String,
    val dob: String,
    val gender: Boolean,
    val phoneNumber: String? = null,
    val address: String? = null,
    val userAccount: UserAccount? = null
)

data class UserAccount(
    val email: String
)