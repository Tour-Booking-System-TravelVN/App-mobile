package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.request.CreatePaymentRequest
import com.tanh.tourbooking.data.model.response.CancelTourResponse
import com.tanh.tourbooking.data.model.response.ConfirmPaymentResponse
import com.tanh.tourbooking.data.model.response.CreatePaymentResponse
import com.tanh.tourbooking.data.model.response.GetOrderCodeResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.api.PaymentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: TourBookingApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): PaymentRepository {
    override suspend fun createPayment(request: CreatePaymentRequest, token: String): Result<CreatePaymentResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.createPayment(
                    request = request,
                    token = "Bearer $token"
                )
            }
        }
    }


    override suspend fun confirmPayment(
        token: String,
        orderCode: String
    ): Result<ConfirmPaymentResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.confirmPayment(
                    token = "Bearer $token",
                    orderCode = orderCode
                )
            }
        }
    }

    override suspend fun getOrderCode(
        token: String,
        orderCode: String
    ): Result<GetOrderCodeResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.getOrderCode(
                    token = "Bearer $token",
                    orderCode = orderCode
                )
            }
        }
    }

    override suspend fun cancelTour(
        token: String,
        bookingId: String
    ): Result<CancelTourResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.cancelTour(
                    token = "Bearer $token",
                    bookingId = bookingId
                )
            }
        }
    }
}