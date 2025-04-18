package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.response.RatingResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.api.RatingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(
    private val api: TourBookingApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): RatingRepository {
    override suspend fun getRatingByTourId(tourId: String): Result<RatingResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.getRatingByTourUnitId(tourId)
            }
        }
    }
}