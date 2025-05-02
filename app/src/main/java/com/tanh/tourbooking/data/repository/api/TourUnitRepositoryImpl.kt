package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.request.RatingTourRequest
import com.tanh.tourbooking.data.model.response.GetTourResponse
import com.tanh.tourbooking.data.model.response.RatingTourResponse
import com.tanh.tourbooking.data.model.response.TourUnitByPlaceResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.di.IODispatcher
import com.tanh.tourbooking.domain.repository.api.TourUnitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TourUnitRepositoryImpl @Inject constructor(
    private val api: TourBookingApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): TourUnitRepository {
    override suspend fun getToursByPlace(
        destination: String,
        departureDate: String?,
        price: String,
        page: Int?
    ): Result<TourUnitByPlaceResponse, NetworkError> {
        return safeCall {
            api.findTourByPlace(
                place = destination,
                price = price,
                departureDate = departureDate,
                page = page
            )
        }
    }

    override suspend fun getMyTour(
        token: String,
        page: Int,
        status: String
    ): Result<GetTourResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.getMyTours(
                    token = "Bearer $token",
                    status = status,
                    page = page
                )
            }
        }
    }

    override suspend fun ratingTour(
        token: String,
        body: RatingTourRequest
    ): Result<RatingTourResponse, NetworkError> {
        return withContext(dispatcher) {
            safeCall {
                api.ratingTour(
                    token = "Bearer $token",
                    request = body
                )
            }
        }
    }
}