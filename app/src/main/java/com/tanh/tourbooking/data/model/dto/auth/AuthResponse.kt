package com.tanh.tourbooking.data.model.dto.auth

data class AuthResponse(
    val code: Int,
    val result: AuthResult
)

data class AuthResult(
    val token: String,
    val fullname: String,
    val role: String,
    val authenticated: Boolean
)