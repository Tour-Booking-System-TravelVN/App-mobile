package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.response.TourUnitByPlaceResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result

interface TourUnitRepository {
    suspend fun getToursByPlace(
        destination: String,
        departureDate: String?,
        price: String,
        page: Int?
    ): Result<TourUnitByPlaceResponse, NetworkError>
}
