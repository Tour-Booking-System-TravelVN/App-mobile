package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.response.RatingResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result

interface RatingRepository {
    suspend fun getRatingByTourId(tourId: String): Result<RatingResponse, NetworkError>
}