package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.TourDto
import com.tanh.tourbooking.domain.model.Tour

fun TourDto.toTour(): Tour =
    Tour(
        category = category.toCategory(),
        createdTime = createdTime?.isoToLocalDateTime(),
        cuisine = cuisine,
        departurePlace = departurePlace,
        description = description,
        duration = duration,
        exclusions = exclusions,
        firstImageUrl = firstImageUrl,
        idealTime = idealTime,
        imageSet = imageSet.map { it.toImageSet() },
        inclusions = inclusions,
        lastUpdatedOperator = lastUpdatedOperator?.toString(),
        lastUpdatedTime = lastUpdatedTime?.isoToLocalDateTime(),
        placesToVisit = placesToVisit,
        targetAudience = targetAudience,
        tourId = tourId,
        tourName = tourName,
        tourOperator = tourOperator?.toString(),
        tourProgramSet = tourProgramSet,
        vehicle = vehicle
    )

fun Tour.toTourDto(): TourDto =
    TourDto(
        category = category.toCategoryDto(),
        createdTime = createdTime?.toFormattedString(),
        cuisine = cuisine,
        departurePlace = departurePlace,
        description = description,
        duration = duration,
        exclusions = exclusions,
        firstImageUrl = firstImageUrl,
        idealTime = idealTime,
        imageSet = imageSet.map { it.toImageSetDto() } ,
        inclusions = inclusions,
        lastUpdatedOperator = lastUpdatedOperator?.toIntOrNull(),
        lastUpdatedTime = lastUpdatedTime?.toFormattedString(),
        placesToVisit = placesToVisit,
        targetAudience = targetAudience,
        tourId = tourId,
        tourName = tourName,
        tourOperator = tourOperator?.toIntOrNull(),
        tourProgramSet = tourProgramSet,
        vehicle = vehicle
    )
