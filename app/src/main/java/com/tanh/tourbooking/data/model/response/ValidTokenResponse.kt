package com.tanh.tourbooking.data.model.response

data class ValidTokenResponse(
    val code: Int,
    val result: Validation
)

data class Validation(
    val valid: Boolean
)