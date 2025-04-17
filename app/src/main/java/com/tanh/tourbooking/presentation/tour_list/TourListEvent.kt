package com.tanh.tourbooking.presentation.tour_list

sealed class TourListEvent {
    data class OnClickTour(val id: String) : TourListEvent()
}