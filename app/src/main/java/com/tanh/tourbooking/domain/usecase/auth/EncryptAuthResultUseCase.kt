package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.model.dto.auth.AuthResult
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import javax.inject.Inject

class EncryptAuthResultUseCase @Inject constructor(
    private val repository: AuthSecurityRepository
) {
    suspend operator fun invoke(data: AuthResult?) {
        data?.apply {
            repository.updateData(data)
        }
    }

}