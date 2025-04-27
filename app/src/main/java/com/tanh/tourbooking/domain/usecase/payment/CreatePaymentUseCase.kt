package com.tanh.tourbooking.domain.usecase.payment

import com.tanh.tourbooking.data.mappers.toTransactionDetail
import com.tanh.tourbooking.data.model.dto.tour.UserAccount
import com.tanh.tourbooking.data.model.request.BookingRequest
import com.tanh.tourbooking.data.model.request.CompanionRequest
import com.tanh.tourbooking.data.model.request.CreatePaymentRequest
import com.tanh.tourbooking.data.model.request.CustomerRequest
import com.tanh.tourbooking.data.model.util.exception.Resources
import com.tanh.tourbooking.data.model.util.exception.Result
import com.tanh.tourbooking.data.model.util.exception.toMessage
import com.tanh.tourbooking.domain.model.Companion
import com.tanh.tourbooking.domain.model.TransactionDetail
import com.tanh.tourbooking.domain.repository.AuthSecurityRepository
import com.tanh.tourbooking.domain.repository.api.PaymentRepository
import com.tanh.tourbooking.presentation.booking.item.InforCustomer
import com.tanh.tourbooking.presentation.detail_tour.BookingTourState
import javax.inject.Inject

class CreatePaymentUseCase @Inject constructor(
    private val authSecurityRepository: AuthSecurityRepository,
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(
        tourState: BookingTourState,
        companions: List<Companion>,
        customerInfo: InforCustomer
    ): Resources<TransactionDetail, Exception> {
        val token = authSecurityRepository.readData().token
        if(token.isNullOrBlank()) {
            return Resources.Error(Exception("Vui lòng đăng nhập/ đăng ký"))
        }
        val request = CreatePaymentRequest(
            cancelUrl = "https://makeitsoapp-44995.web.app/failure",
            returnUrl = "https://makeitsoapp-44995.web.app/success",
            description = tourState.tourUnitId,
            price = tourState.totalPrice.toInt(),
            productName = tourState.tourName,
            bookingRequest = BookingRequest(
                customer = CustomerRequest(
                    firstname = customerInfo.firstname,
                    lastname = customerInfo.lastname,
                    dob = customerInfo.dob,
                    gender = customerInfo.gender,
                    phoneNumber = customerInfo.phoneNumber,
                    address = customerInfo.address,
                    userAccount = UserAccount(
                        email = customerInfo.email
                    )
                ),
                tourUnitId = tourState.tourUnitId,
                babyNumber = tourState.babyNumber,
                toddlerNumber = tourState.toddleNumber,
                childNumber = tourState.childNumber,
                adultNumber = tourState.adultNumber,
                privateRoomNumber = 1,
                note = "Không",
                totalAmount = tourState.totalPrice.toInt(),
                companions = companions.map { companion ->
                    CompanionRequest(
                        customer = CustomerRequest(
                            firstname = companion.firstName,
                            lastname = companion.lastName,
                            dob = companion.dob,
                            gender = companion.gender
                        )
                    )
                }
            )
        )

        return paymentRepository.createPayment(
            request = request,
            token = token
        ).let {
            when(val result = it) {
                is Result.Error -> {
                    Resources.Error(Exception(result.error.toMessage()))
                }
                is Result.Success -> {
                    if(result.data.error == -1) {
                        Resources.Error(Exception(result.data.message))
                    } else {
                        Resources.Success(result.data.data.toTransactionDetail())
                    }
                }
            }
        }
    }

}