package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.request.CreatePaymentRequest
import com.tanh.tourbooking.data.model.response.CreatePaymentResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result

interface PaymentRepository {
    suspend fun createPayment(request: CreatePaymentRequest, token: String): Result<CreatePaymentResponse, NetworkError>
}