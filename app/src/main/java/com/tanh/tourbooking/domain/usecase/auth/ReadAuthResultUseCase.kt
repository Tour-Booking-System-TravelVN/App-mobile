package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.model.dto.auth.AuthResult
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import javax.inject.Inject

class ReadAuthResultUseCase @Inject constructor(
    private val repository: AuthSecurityRepository
) {

    suspend operator fun invoke(): AuthResult {
        return repository.readData()
    }

}