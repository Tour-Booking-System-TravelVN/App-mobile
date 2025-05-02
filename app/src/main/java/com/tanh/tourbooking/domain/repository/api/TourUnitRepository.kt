package com.tanh.tourbooking.domain.repository.api

import android.net.NetworkRequest
import com.tanh.tourbooking.data.model.request.RatingTourRequest
import com.tanh.tourbooking.data.model.response.GetTourResponse
import com.tanh.tourbooking.data.model.response.RatingTourResponse
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

    suspend fun getMyTour(token: String, page: Int, status: String): Result<GetTourResponse, NetworkError>

    suspend fun ratingTour(token: String, body: RatingTourRequest): Result<RatingTourResponse, NetworkError>
}
