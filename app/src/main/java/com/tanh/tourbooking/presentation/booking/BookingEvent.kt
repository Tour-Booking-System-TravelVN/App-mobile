package com.tanh.tourbooking.presentation.booking

import android.content.Context
import com.tanh.tourbooking.domain.model.Companion

sealed class BookingEvent {
    data class AddCompanion(
        val companion: Companion
    ) : BookingEvent()

    data class AddContactInformation(
        val firstname: String?,
        val lastname: String?,
        val dob: String?,
        val gender: Boolean?,
        val phoneNumber: String?,
        val address: String?,
        val email: String?
    ) : BookingEvent()

    data object MakeUrlPayment : BookingEvent()
    data class MakeZaloPayment(val context: Context) : BookingEvent()
}