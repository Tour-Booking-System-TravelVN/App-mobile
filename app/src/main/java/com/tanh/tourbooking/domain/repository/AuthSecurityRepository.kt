package com.tanh.tourbooking.domain.repository

import com.tanh.tourbooking.data.model.dto.auth.AuthResult

interface AuthSecurityRepository {
    suspend fun updateData(data: AuthResult)
    suspend fun readData(): AuthResult
}