package com.tanh.tourbooking.data.model.dto.tour

data class TourUnitCalendarDto(
    val adultTourPrice: Double,
    val availableCapacity: Int,
    val babyTourPrice: Double,
    val childTourPrice: Double,
    val departureDate: String,
    val discount: DiscountDto,
    val festival: FestivalDto,
    val maximumCapacity: Int,
    val privateRoomPrice: Double,
    val returnDate: String,
    val toddlerTourPrice: Double,
    val tourUnitId: String
)