package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.model.dto.auth.AuthResult
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authSecurityRepository: AuthSecurityRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        val token = authSecurityRepository.readData().token
        if (token.isNullOrBlank()) return false
        return authRepository.logout(token).let {
            when (val result = it) {
                is Result.Error -> false
                is Result.Success -> {
                    if(result.data.code == 0) {
                        authSecurityRepository.updateData(data = AuthResult())
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }

}