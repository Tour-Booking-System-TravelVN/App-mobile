package com.tanh.tourbooking.presentation.profile

sealed class ProfileEvent {
    data class OnNavToPrivateInformation(val route: String): ProfileEvent()
    data object Logout : ProfileEvent()
}