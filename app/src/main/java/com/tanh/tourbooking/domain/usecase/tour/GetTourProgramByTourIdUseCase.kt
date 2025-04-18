package com.tanh.tourbooking.domain.usecase.tour

import com.tanh.tourbooking.data.mappers.toTourProgram
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.TourProgram
import com.tanh.tourbooking.domain.repository.api.TourProgramRepository
import javax.inject.Inject

class GetTourProgramByTourIdUseCase @Inject constructor(
    private val repository: TourProgramRepository
) {
    suspend operator fun invoke(tourId: String): Resources<List<TourProgram>, Exception> {
        return when(val response = repository.getTourProgramByTourId(tourId)) {
            is Result.Error -> {
                Resources.Error(Exception(response.error.toMessage()))
            }
            is Result.Success -> {
                Resources.Success(response.data.result.map { it.toTourProgram() })
            }
        }
    }
}