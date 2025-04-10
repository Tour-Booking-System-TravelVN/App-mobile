package com.tanh.tourbooking.presentation.register

data class RegisterState(
    val isLoading: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val errorMessage: String? = null,
    val input: RegisterInputValue = RegisterInputValue()
)

data class RegisterInputValue(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val dateOfBirth: String = "",
    val gender: Boolean = false,
    val nationality: String = "",
    val citizenId: String = "",
    val passport: String = "",
    val phoneNumber: String = "",
    val address: String = ""
)
