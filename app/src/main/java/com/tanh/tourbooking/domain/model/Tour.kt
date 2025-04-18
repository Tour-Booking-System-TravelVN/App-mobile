package com.tanh.tourbooking.domain.model

import com.tanh.tourbooking.data.serializer.LocalDateSerializer
import com.tanh.tourbooking.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Tour(
    val category: Category,
    @Serializable(with = LocalDateTimeSerializer::class)
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
    @Serializable(with = LocalDateTimeSerializer::class)
    val lastUpdatedTime: LocalDateTime?,
    val placesToVisit: String,
    val targetAudience: String,
    val tourId: String,
    val tourName: String,
    val tourOperator: String?,
    val tourProgramSet: String?,
    val vehicle: String
)