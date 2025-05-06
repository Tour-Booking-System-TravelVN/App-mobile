package com.tanh.tourbooking.domain.usecase.payment

import com.tanh.tourbooking.data.mappers.toConfirmedData
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.ConfirmedData
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.PaymentRepository
import javax.inject.Inject

class ConfirmPaymentUseCase @Inject constructor(
    private val authSecurityRepository: AuthSecurityRepository,
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(orderCode: String): Resources<ConfirmedData, Exception> {
        val token = authSecurityRepository.readData().token
        if (token.isNullOrBlank()) {
            return Resources.Error(Exception("Vui lòng đăng nhập/ đăng ký"))
        }

        return paymentRepository.confirmPayment(
            token = token,
            orderCode = orderCode
        ).let { result ->
            when (result) {
                is com.tanh.tourbooking.data.model.util.exception.Result.Error -> {
                    Resources.Error(Exception(result.error.toMessage()))
                }
                is Result.Success -> {
                    val response = result.data
                    if (response.error == -1) {
                        Resources.Error(Exception(response.message))
                    } else {
                        Resources.Success(response.confirmedData.toConfirmedData())
                    }
                }
            }
        }
    }
}