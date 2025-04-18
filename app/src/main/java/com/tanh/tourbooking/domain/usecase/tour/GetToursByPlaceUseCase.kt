package com.tanh.tourbooking.domain.usecase.tour

import com.tanh.tourbooking.data.mappers.toTourUnit
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.TourUnit
import com.tanh.tourbooking.domain.repository.api.TourUnitRepository
import javax.inject.Inject

class GetToursByPlaceUseCase @Inject constructor(
    private val tourUnitRepository: TourUnitRepository
) {
    suspend operator fun invoke(
        destination: String,
        departureDate: String?,
        price: String,
        page: Int?
    ): Resources<List<TourUnit>, Exception> {
        val response = tourUnitRepository.getToursByPlace(
            destination = destination,
            departureDate = departureDate,
            price = price,
            page = page
        )
        return when(response) {
            is Result.Success -> {
                Resources.Success(response.data.result.map { it.toTourUnit() })
            }
            is Result.Error -> {
                Resources.Error(Exception(response.error.toMessage()))
            }
        }
    }

}