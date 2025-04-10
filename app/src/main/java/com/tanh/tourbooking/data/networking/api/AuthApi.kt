package com.tanh.tourbooking.data.networking.api

import com.tanh.tourbooking.data.model.dto.auth.AuthResponse
import com.tanh.tourbooking.data.model.dto.auth.LoginRequest
import com.tanh.tourbooking.data.model.dto.auth.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/tokenapp")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("/registerapp")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

}