package com.tanh.tourbooking.domain.usecase.tour

import com.tanh.tourbooking.data.mappers.toPaymentInformation
import com.tanh.tourbooking.data.model.request.PaymentRequest
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.domain.model.PaymentInformation
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.BookingRepository
import javax.inject.Inject

class CreateBookingOrderUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val authSecurityRepository: AuthSecurityRepository
) {

    suspend operator fun invoke(
        paymentRequest: PaymentRequest
    ): Resources<PaymentInformation, Exception> {
        val token = authSecurityRepository.readData().token
        if(token.isNullOrBlank()) {
            return Resources.Error(Exception("Vui lòng đăng nhập/đăng ký"))
        }
        return bookingRepository.createOrder(
            data = paymentRequest,
            token = token
        ).let { response ->
            when(response) {
                is Result.Error-> {
                    Resources.Error(Exception())
                }
                is Result.Success -> {
                    Resources.Success(response.data.data.toPaymentInformation())
                }
            }
        }
    }

}