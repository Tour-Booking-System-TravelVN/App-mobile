package com.tanh.tourbooking.data.model.dto.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val status: String = "ON",
//    @SerializedName("c")
//    val customer: CustomerRequest
)

data class CustomerRequest(
    val firstname: String = "",
    val lastname: String = "",
    @SerializedName("dob")
    val dateOfBirth: String = "",
    val gender: Boolean = false,
    val nationality: String = "",
    @SerializedName("citizenid")
    val citizenId: String = "",
    val passport: String = "",
    @SerializedName("phonenumber")
    val phoneNumber: String = "",
    val address: String = ""
)

