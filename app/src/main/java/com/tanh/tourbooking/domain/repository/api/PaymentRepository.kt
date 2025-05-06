package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.request.CreatePaymentRequest
import com.tanh.tourbooking.data.model.response.CancelTourResponse
import com.tanh.tourbooking.data.model.response.ConfirmPaymentResponse
import com.tanh.tourbooking.data.model.response.CreatePaymentResponse
import com.tanh.tourbooking.data.model.response.GetOrderCodeResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result

interface PaymentRepository {
    suspend fun createPayment(request: CreatePaymentRequest, token: String): Result<CreatePaymentResponse, NetworkError>
    suspend fun confirmPayment(
        token: String,
        orderCode: String
    ): Result<ConfirmPaymentResponse, NetworkError>
    suspend fun getOrderCode(
        token: String,
        orderCode: String
    ): Result<GetOrderCodeResponse, NetworkError>
    suspend fun cancelTour(
        token: String,
        bookingId: String
    ): Result<CancelTourResponse, NetworkError>
}