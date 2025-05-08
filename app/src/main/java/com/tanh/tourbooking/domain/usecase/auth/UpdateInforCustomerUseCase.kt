package com.tanh.tourbooking.domain.usecase.auth

import com.tanh.tourbooking.data.model.request.UpdateInfoCustomerRequest
import com.tanh.tourbooking.data.model.response.UpdateInfoResponse
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.UserRepository
import javax.inject.Inject

class UpdateInforCustomerUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authSecurityRepository: AuthSecurityRepository
) {
    suspend operator fun invoke(request: UpdateInfoCustomerRequest): Resources<Boolean, Exception> {
        val token = authSecurityRepository.readData().token
        if(token.isNullOrBlank()) {
            return Resources.Error(Exception("Vui lòng đăng nhập/ đăng ký"))
        }
        return userRepository.updateInfoCustomer(
            request = request,
            token = token
        ).let { result ->
            when(result) {
                is Result.Error -> Resources.Error(Exception(result.error.toMessage()))
                is Result.Success -> Resources.Success(true)
            }
        }
    }
}