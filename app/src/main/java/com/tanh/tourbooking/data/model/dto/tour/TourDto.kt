package com.tanh.tourbooking.data.model.dto.tour

data class TourDto(
    val category: CategoryDto,
    val createdTime: String,
    val cuisine: String,
    val departurePlace: String,
    val description: String?,
    val duration: String,
    val exclusions: String, 
    val firstImageUrl: String,
    val idealTime: String,
    val imageSet: List<ImageSetDto>,
    val inclusions: String,
    val lastUpdatedOperator: String?,
    val lastUpdatedTime: String,
    val placesToVisit: String,
    val targetAudience: String,
    val tourId: String,
    val tourName: String,
    val tourOperator: String?,
    val tourProgramSet: String?,
    val vehicle: String
)