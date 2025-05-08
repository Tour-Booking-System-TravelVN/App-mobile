package com.tanh.tourbooking.presentation.profile

import com.tanh.tourbooking.domain.model.Information
import com.tanh.tourbooking.util.Role

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val customer: Information.Customer? = null,
    val tourGuide: Information.TourGuide? = null,
    val role: Role = Role.NULL,
)

data class InputInformationState(
    val isLoading: Boolean = false,

    val oldPassword: String? = null,
    val newPassword: String? = null,
    val errorPassword: String? = null,

    val address: String? = null,
    val citizenId: String? = null,
    val dateOfBirth: String? = null,
    val firstname: String? = null,
    val gender: Boolean? = null,
    val lastname: String? = null,
    val nationality: String? = null,
    val note: String? = null,
    val passport: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,

    val firstnameError: String? = null,
    val lastnameError: String? = null,
    val dateOfBirthError: String? = null,
    val addressError: String? = null,
    val citizenIdError: String? = null,
    val nationalityError: String? = null,
    val passportError: String? = null,
    val phoneNumberError: String? = null,
    val emailError: String? = null

)