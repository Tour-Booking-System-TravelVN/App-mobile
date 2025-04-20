package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.request.BookingRequest
import com.tanh.tourbooking.data.model.request.PaymentRequest
import com.tanh.tourbooking.data.model.response.BookingResponse
import com.tanh.tourbooking.data.model.response.CheckTourUnitResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result

interface BookingRepository {
    suspend fun checkBeforeBooking(tourUnitId: String, token: String): Result<CheckTourUnitResponse, NetworkError>
    suspend fun createOrder(data: PaymentRequest, token: String): Result<BookingResponse, NetworkError>
}