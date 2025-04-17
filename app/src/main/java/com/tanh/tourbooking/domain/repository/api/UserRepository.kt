package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.response.UserInformationResponse
import com.tanh.tourbooking.data.model.response.UserInformationResult
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.domain.model.FakCustomer
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getInformation(token: String): Result<UserInformationResponse, NetworkError>

    suspend fun getUsers(): Flow<List<FakCustomer>>
    suspend fun checkBookingIdTour(bookingId: Int): Boolean
    fun getUserId(): Int?
}