package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.dto.auth.AuthResponse
import com.tanh.tourbooking.data.model.dto.auth.LoginRequest
import com.tanh.tourbooking.data.model.dto.auth.RegisterRequest
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tourBookingApi: TourBookingApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun login(request: LoginRequest): Result<AuthResponse, NetworkError> {
        return safeCall {
            tourBookingApi.login(request)
        }
    }

    override suspend fun register(request: RegisterRequest): Result<AuthResponse, NetworkError> {
        return safeCall {
            tourBookingApi.register(request)
        }
    }
}