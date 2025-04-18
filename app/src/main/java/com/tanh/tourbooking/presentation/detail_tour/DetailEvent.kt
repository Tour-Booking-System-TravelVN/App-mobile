package com.tanh.tourbooking.presentation.detail_tour

sealed class DetailEvent {
    data object BookTour : DetailEvent()
}