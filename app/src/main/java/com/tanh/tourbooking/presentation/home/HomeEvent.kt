package com.tanh.tourbooking.presentation.home

sealed class HomeEvent {
    data class OnNavToTours(val route: String): HomeEvent()
}