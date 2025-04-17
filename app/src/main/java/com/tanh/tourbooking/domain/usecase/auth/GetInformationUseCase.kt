package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.mappers.toCustomer
import com.tanh.tourbooking.data.mappers.toTourGuide
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.UserRepository
import com.tanh.tourbooking.util.Role
import javax.inject.Inject

class GetInformationUseCase @Inject constructor(
    private val repository: UserRepository,
    private val authSecurity: AuthSecurityRepository
) {

    suspend operator fun invoke(): Resources<Information, Exception> {
        val data = authSecurity.readData()
        if (data.token == null) {
            return Resources.Error(Exception("Login again"))
        }

        val role = data.role
        return when (val response = repository.getInformation("Bearer ${data.token}")) {
            is Result.Success -> {
                when (role) {
                    Role.CUSTOMER.toString() -> {
                        val customerDto = response.data.result.c
                        val customer = customerDto?.toCustomer()
                        Resources.Success(customer!!)
                    }

                    Role.TOURGUIDE.toString() -> {
                        val tourGuideDto = response.data.result.tourGuide
                        val tourGuide = tourGuideDto?.toTourGuide()
                        Resources.Success(tourGuide!!)
                    }

                    else -> {
                        Resources.Error(Exception("Oops"))
                    }
                }
            }

            is Result.Error -> {
                Resources.Error(Exception(response.error.toMessage()))
            }
        }
    }

}
