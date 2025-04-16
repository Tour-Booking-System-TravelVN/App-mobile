package com.tanh.tourbooking.data.model.dto.tour

data class TourUnitDto(
    val adultTourCost: Double,
    val adultTourPrice: Double,
    val availableCapacity: Int,
    val babyTourCost: Double,
    val babyTourPrice: Double,
    val childTourCost: Double,
    val childTourPrice: Double,
    val createdTime: String?,
    val departureDate: String,
    val discount: DiscountDto?,
    val festival: FestivalDto?,
    val lastUpdatedOperator: String?,
    val lastUpdatedTime: String?,
    val maximumCapacity: Int,
    val privateRoomPrice: Double,
    val returnDate: String,
    val toddlerTourCost: Double,
    val toddlerTourPrice: Double,
    val totalAdditionalCost: Double,
    val tour: TourDto,
    val tourOperator: String?,
    val tourUnitId: String
)