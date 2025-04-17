package com.tanh.tourbooking.domain.model

import java.time.LocalDateTime

data class Tour(
    val category: Category,
    val createdTime: LocalDateTime?,
    val cuisine: String,
    val departurePlace: String,
    val description: String?,
    val duration: String,
    val exclusions: String,
    val firstImageUrl: String,
    val idealTime: String,
    val imageSet: List<ImageSet>,
    val inclusions: String,
    val lastUpdatedOperator: String?,
    val lastUpdatedTime: LocalDateTime?,
    val placesToVisit: String,
    val targetAudience: String,
    val tourId: String,
    val tourName: String,
    val tourOperator: String?,
    val tourProgramSet: String?,
    val vehicle: String
)