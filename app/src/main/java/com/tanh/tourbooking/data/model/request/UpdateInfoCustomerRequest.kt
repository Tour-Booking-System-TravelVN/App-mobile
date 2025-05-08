package com.tanh.tourbooking.data.model.request

import com.google.gson.annotations.SerializedName

data class UpdateInfoCustomerRequest(
    val email: String?,
    @SerializedName("c")
    val customer: UpdateInfo
)

data class UpdateInfo(
    val address: String?,
    val citizenId: String?,
    val dateOfBirth: String?,
    val firstname: String?,
    val gender: Boolean?,
    val lastname: String?,
    val nationality: String?,
    val note: String?,
    val passport: String?,
    val phoneNumber: String?
)
