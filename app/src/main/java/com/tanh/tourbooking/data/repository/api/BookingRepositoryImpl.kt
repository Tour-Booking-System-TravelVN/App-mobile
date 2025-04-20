package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.request.BookingRequest
import com.tanh.tourbooking.data.model.request.PaymentRequest
import com.tanh.tourbooking.data.model.response.BookingResponse
import com.tanh.tourbooking.data.model.response.CheckTourUnitResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.api.BookingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val api: TourBookingApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): BookingRepository {

    override suspend fun checkBeforeBooking(
        tourUnitId: String,
        token: String
    ): Result<CheckTourUnitResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.checkBeforeBooking(
                    token = "Bearer $token",
                    tourUnitId = tourUnitId
                )
            }
        }
    }

    override suspend fun createOrder(
        data: PaymentRequest,
        token: String
    ): Result<BookingResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.createBooking(
                    token = "Bearer $token",
                    request = data
                )
            }
        }
    }
}