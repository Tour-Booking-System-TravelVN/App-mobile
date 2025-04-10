package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.model.dto.auth.LoginRequest
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String) =
        repository.login(LoginRequest(username, password))

}