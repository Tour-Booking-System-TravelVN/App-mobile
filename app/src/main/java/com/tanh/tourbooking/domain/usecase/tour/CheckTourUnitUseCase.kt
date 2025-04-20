package com.tanh.tourbooking.domain.usecase.tour

import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.BookingRepository
import javax.inject.Inject

class CheckTourUnitUseCase @Inject constructor(
    private val authSecurityRepository: AuthSecurityRepository,
    private val repository: BookingRepository
) {
    suspend operator fun invoke(tourUnitId: String): Resources<Boolean, Exception> {
        val token = authSecurityRepository.readData().token
        if(token.isNullOrBlank()) {
            return Resources.Error(Exception("Vui lòng đăng nhập/ đăng ký"))
        }
        return repository.checkBeforeBooking(tourUnitId, token).let {
            when(it) {
                is Result.Success -> {
                    Resources.Success(true)
                }
                is Result.Error -> {
                    Resources.Success(false)
                }
            }
        }
    }
}