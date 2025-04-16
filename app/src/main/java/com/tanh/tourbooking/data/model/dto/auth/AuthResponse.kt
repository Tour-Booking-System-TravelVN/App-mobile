package com.tanh.tourbooking.data.model.dto.auth

import kotlinx.serialization.Serializable

data class AuthResponse(
    val code: Int,
    val result: AuthResult
)

@Serializable
data class AuthResult(
    val token: String? = null,
    val fullname: String? = null,
    val role: String? = null,
    val authenticated: Boolean = false
)