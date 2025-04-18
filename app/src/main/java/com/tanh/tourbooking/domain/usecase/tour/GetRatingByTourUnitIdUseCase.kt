package com.tanh.tourbooking.domain.usecase.tour

import com.tanh.tourbooking.data.mappers.toRating
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.Rating
import com.tanh.tourbooking.domain.repository.api.RatingRepository
import javax.inject.Inject

class GetRatingByTourUnitIdUseCase @Inject constructor(
    private val repository: RatingRepository
) {
    suspend operator fun invoke(tourId: String): Resources<List<Rating>, Exception> {
        return when(val result = repository.getRatingByTourId(tourId)) {
            is Result.Error -> {
                val error = result.error
                Resources.Error(Exception(error.toMessage()))
            }
            is Result.Success -> {
                Resources.Success(result.data.result.map { it.toRating() })
            }
        }
    }
}