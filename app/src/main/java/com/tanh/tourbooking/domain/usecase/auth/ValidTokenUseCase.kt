package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.AuthRepository
import javax.inject.Inject

class ValidTokenUseCase @Inject constructor(
    private val authSecurityRepository: AuthSecurityRepository,
    private val authRepository: AuthRepository
) {
    
    suspend operator fun invoke(): Boolean {
        val token = authSecurityRepository.readData().token
        if(token.isNullOrBlank()) return false
        return authRepository.validToken(token).let {
            when(val result = it) {
                is Result.Success -> {
                    if(result.data.code == 0) {
                        true
                    } else {
                        false
                    }
                }
                is Result.Error -> {
                    false
                }
            }
        }
    }
    
}