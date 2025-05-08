package com.tanh.tourbooking.presentation.profile

sealed class ProfileEvent {
    data class onNavToScreen(val route: String): ProfileEvent()
    data object SaveEditedInformation : ProfileEvent()
    data object ChangePassword : ProfileEvent()
    data object Logout : ProfileEvent()
}