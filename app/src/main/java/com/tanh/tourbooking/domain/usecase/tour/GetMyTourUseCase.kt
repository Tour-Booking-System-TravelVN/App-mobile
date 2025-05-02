package com.tanh.tourbooking.domain.usecase.tour

import com.tanh.tourbooking.data.mappers.toMyTour
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.MyTour
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.TourUnitRepository
import javax.inject.Inject

class GetMyTourUseCase @Inject constructor(
    private val authSecurityRepository: AuthSecurityRepository,
    private val tourUnitRepository: TourUnitRepository
) {
    suspend operator fun invoke(status: String, page: Int): Resources<List<MyTour>, Exception> {
        val token = authSecurityRepository.readData().token
        if(token.isNullOrBlank()) {
            return Resources.Error(Exception("Vui lòng đăng nhập/đăng ký"))
        }
        return tourUnitRepository.getMyTour(token = token, status = status, page = page).let { result ->
            when(result) {
                is Result.Error -> Resources.Error(Exception(result.error.toMessage()))
                is Result.Success -> Resources.Success(result.data.result.myTourDto.map { it.toMyTour() })
            }
        }
    }

}