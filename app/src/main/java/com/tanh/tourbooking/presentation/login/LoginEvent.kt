package com.tanh.tourbooking.presentation.login

sealed class LoginEvent {
    data class NavToRegister(val route: String) : LoginEvent()
    data class Login(val username: String, val password: String) : LoginEvent()
}