package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.response.TourProgramResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.api.TourProgramRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TourProgramRepositoryImpl @Inject constructor(
    private val api: TourBookingApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): TourProgramRepository {
    override suspend fun getTourProgramByTourId(tourId: String): Result<TourProgramResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.getTourProgramByTourUnitId(tourId)
            }
        }
    }

}