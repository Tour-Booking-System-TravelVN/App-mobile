package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.model.dto.auth.AuthResponse
import com.tanh.tourbooking.data.model.dto.auth.CustomerRequest
import com.tanh.tourbooking.data.model.dto.auth.RegisterRequest
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        email: String
    ): Result<AuthResponse, NetworkError> {
        val customer = CustomerRequest(
            firstname = "Anh bawdbaw",
            lastname = "bUibuiuaw"
        )
        return repository.register(RegisterRequest(
            username = username,
            password = password,
            email = email,
//            customer = customer
        ))
    }

}