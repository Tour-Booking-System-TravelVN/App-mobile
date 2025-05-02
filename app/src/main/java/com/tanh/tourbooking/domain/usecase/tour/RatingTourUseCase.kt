package com.tanh.tourbooking.domain.usecase.tour

import com.google.common.eventbus.SubscriberExceptionContext
import com.tanh.tourbooking.data.model.request.RatingTourRequest
import com.tanh.tourbooking.data.model.response.RatingTourResponse
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.TourUnitRepository
import javax.inject.Inject

class RatingTourUseCase @Inject constructor(
    private val authSecurityRepository: AuthSecurityRepository,
    private val tourUnitRepository: TourUnitRepository
) {
    suspend operator fun invoke(
        tourUnitId: String,
        ratingValue: Int,
        comment: String
    ): Resources<RatingTourResponse, Exception> {
        val token = authSecurityRepository.readData().token
        if (token.isNullOrBlank()) {
            return Resources.Error(Exception("Vui lòng đăng nhập/đăng ký"))
        }
        val body = RatingTourRequest(
            tourUnitId = tourUnitId,
            ratingValue = ratingValue,
            comment = comment
        )
        return tourUnitRepository.ratingTour(token, body).let { result ->
            when (result) {
                is com.tanh.tourbooking.data.model.util.exception.Result.Error -> Resources.Error(
                    Exception(Exception(result.error.toMessage()))
                )

                is Result.Success -> Resources.Success(result.data)
            }
        }
    }
}