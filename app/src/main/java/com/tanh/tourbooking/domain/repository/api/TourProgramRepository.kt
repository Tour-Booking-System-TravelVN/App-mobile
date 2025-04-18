package com.tanh.tourbooking.domain.repository.api

import com.tanh.tourbooking.data.model.response.TourProgramResponse
import com.tanh.tourbooking.data.model.util.exception.NetworkError
import com.tanh.tourbooking.data.model.util.exception.Result

interface TourProgramRepository {
    suspend fun getTourProgramByTourId(tourId: String): Result<TourProgramResponse, NetworkError>
}