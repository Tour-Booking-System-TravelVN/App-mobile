package com.tanh.tourbooking.presentation.detail_tour

sealed class DetailEvent {
    data class BookTour(val state: BookingTourState) : DetailEvent()
    data object OnLoadCalendar : DetailEvent()
}