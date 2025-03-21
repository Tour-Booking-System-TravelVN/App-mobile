package com.tanh.tourbooking.presentation.util

sealed class OneTimeEvent {
    data class Navigate(val route: String) : OneTimeEvent()
    data class ShowSnackbar(val message: String) : OneTimeEvent()
    data class ShowToast(val message: String) : OneTimeEvent()
    data object PopBackStack : OneTimeEvent()
}