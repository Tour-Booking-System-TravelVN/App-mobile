package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.util.Role
import javax.inject.Inject

class CheckRoleUseCase @Inject constructor(
    private val repository: AuthSecurityRepository
) {
    suspend operator fun invoke(): Role {
        val data = repository.readData()
        val currentRole = data.role ?: "NULL"
        return when (currentRole) {
            Role.CUSTOMER.toString() -> {
                Role.CUSTOMER
            }
            Role.TOURGUIDE.toString() -> {
                Role.TOURGUIDE
            }
            else -> {
                Role.NULL
            }
        }
    }

}