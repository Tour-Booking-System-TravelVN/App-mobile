package com.tanh.tourbooking.data.repository.api

import com.google.gson.Gson
import com.tanh.tourbooking.data.model.request.ChangePasswordRequest
import com.tanh.tourbooking.data.model.request.UpdateInfoCustomerRequest
import com.tanh.tourbooking.data.model.response.UpdateInfoResponse
import com.tanh.tourbooking.data.model.response.UserInformationResponse
import com.tanh.tourbooking.data.model.response.UserInformationResult
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.NetworkingError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.data.networking.util.safeCallWith400
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.model.FakCustomer
import com.tanh.tourbooking.domain.model.FakTourGuide
import com.tanh.tourbooking.domain.repository.api.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val tourBookingApi: TourBookingApi,
    private val gson: Gson,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getInformation(token: String): Result<UserInformationResponse, NetworkError> {
        return safeCall {
            tourBookingApi.getInformation(token)
        }
    }

    override suspend fun updateInfoCustomer(
        request: UpdateInfoCustomerRequest,
        token: String
    ): Result<UpdateInfoResponse, NetworkingError> {
        return safeCallWith400(
            execute = {
                tourBookingApi.updateInfoCustomer(
                    token = "Bearer $token",
                    body = request
                )
            },
            gson = gson
        )
    }

    override suspend fun changePassword(
        request: ChangePasswordRequest,
        token: String
    ): Result<Boolean, NetworkingError> {
        return safeCallWith400(
            execute = {
                tourBookingApi.changePassword(
                    token = "Bearer $token",
                    body = request
                )
            },
            gson = gson
        )
    }

    override suspend fun getUsers(): Flow<List<FakCustomer>> {
        return flow {
            emit(users)
        }
    }

    override fun getUserId(): Int? {
        return 1;
    }

    override suspend fun checkBookingIdTour(bookingId: String): Boolean {
        return true
    }

}

val users = listOf(
    FakCustomer(username = "user1", password = "password1", id = 1),
    FakCustomer(username = "user2", password = "password2", id = 2),
    FakCustomer(username = "user3", password = "password3", id = 3),
    FakCustomer(username = "user4", password = "password4", id = 4),
    FakCustomer(username = "user5", password = "password5", id = 5),
)

val tourGuide = FakTourGuide(1, "name", "email")

const val tourId = 1