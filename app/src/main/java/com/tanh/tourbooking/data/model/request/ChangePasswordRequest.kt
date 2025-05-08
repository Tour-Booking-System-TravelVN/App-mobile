package com.tanh.tourbooking.data.model.request

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
