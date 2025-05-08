package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.request.ChangePasswordRequest
import com.tanh.tourbooking.data.model.request.UpdateInfoCustomerRequest
import com.tanh.tourbooking.data.model.response.UpdateInfoResponse
import com.tanh.tourbooking.data.model.response.UserInformationResponse
import com.tanh.tourbooking.data.model.response.UserInformationResult
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.NetworkingError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.domain.model.FakCustomer
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getInformation(token: String): Result<UserInformationResponse, NetworkError>

    suspend fun getUsers(): Flow<List<FakCustomer>>
    suspend fun checkBookingIdTour(bookingId: String): Boolean
    suspend fun updateInfoCustomer(request: UpdateInfoCustomerRequest, token: String): Result<UpdateInfoResponse, NetworkingError>
    suspend fun changePassword(request: ChangePasswordRequest, token: String): Result<Boolean, NetworkingError>

    fun getUserId(): Int?
}