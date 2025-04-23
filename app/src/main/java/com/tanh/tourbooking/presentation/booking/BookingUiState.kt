package com.tanh.tourbooking.presentation.booking

import com.tanh.tourbooking.domain.model.Companion
import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.domain.model.TransactionDetail
import com.tanh.tourbooking.presentation.detail_tour.BookingTourState

data class BookingUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val state: BookingTourState = BookingTourState(),

    val companions: List<Companion> = emptyList<Companion>(),

    val transactionDetail: TransactionDetail? = null,

    val editedFirstName: String = "",
    val editedLastName: String = "",
    val editedDob: String = "",
    val editedGender: Boolean = false,
    val editedPhoneNumber: String = "",
    val editedAddress: String = "",
    val editedEmail: String = ""
)