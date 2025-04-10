package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.dto.auth.AuthResponse
import com.tanh.tourbooking.data.model.dto.auth.LoginRequest
import com.tanh.tourbooking.data.model.dto.auth.RegisterRequest
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result

interface AuthRepository {

    suspend fun login(request: LoginRequest): Result<AuthResponse, NetworkError>
    suspend fun register(request: RegisterRequest): Result<AuthResponse, NetworkError>

}