package com.tanh.tourbooking.presentation.register

sealed class RegisterEvent {
    data class OnNavToLogin(val route: String): RegisterEvent()
    data object RegisterAccount: RegisterEvent()
}