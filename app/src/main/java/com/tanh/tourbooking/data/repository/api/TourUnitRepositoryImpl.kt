package com.tanh.tourbooking.data.repository.api

import com.tanh.tourbooking.data.model.response.TourUnitByPlaceResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.networking.api.TourBookingApi
import com.tanh.tourbooking.data.networking.util.safeCall
import com.tanh.tourbooking.domain.repository.api.TourUnitRepository
import javax.inject.Inject

class TourUnitRepositoryImpl @Inject constructor(
    private val api: TourBookingApi
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
}