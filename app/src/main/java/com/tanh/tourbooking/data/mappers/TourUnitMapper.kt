package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.TourUnitDto
import com.tanh.tourbooking.domain.model.TourUnit

fun TourUnitDto.toTourUnit(): TourUnit =
    TourUnit(
        adultTourCost = adultTourCost,
        adultTourPrice = adultTourPrice,
        availableCapacity = availableCapacity,
        babyTourCost = babyTourCost,
        babyTourPrice = babyTourPrice,
        childTourCost = childTourCost,
        childTourPrice = childTourPrice,
        createdTime = createdTime?.isoToLocalDateTime(),
        departureDate = departureDate?.toLocalDate(),
        discount = discount?.toDiscount(),
        festival = festival?.toFestival(),
        lastUpdatedOperator = lastUpdatedOperator,
        lastUpdatedTime = lastUpdatedTime?.isoToLocalDateTime(),
        maximumCapacity = maximumCapacity,
        privateRoomPrice = privateRoomPrice,
        returnDate = returnDate?.toLocalDate(),
        toddlerTourCost = toddlerTourCost,
        toddlerTourPrice = toddlerTourPrice,
        totalAdditionalCost = totalAdditionalCost,
        tour = tour.toTour(),
        tourOperator = tourOperator,
        tourUnitId = tourUnitId
    )

fun TourUnit.toTourUnitDto(): TourUnitDto =
    TourUnitDto(
        adultTourCost = adultTourCost,
        adultTourPrice = adultTourPrice,
        availableCapacity = availableCapacity,
        babyTourCost = babyTourCost,
        babyTourPrice = babyTourPrice,
        childTourCost = childTourCost,
        childTourPrice = childTourPrice,
        createdTime = createdTime?.toFormattedString(),
        departureDate = departureDate?.toFormattedString(),
        discount = discount?.toDiscountDto(),
        festival = festival?.toFestivalDto(),
        lastUpdatedOperator = lastUpdatedOperator,
        lastUpdatedTime = lastUpdatedTime?.toFormattedString(),
        maximumCapacity = maximumCapacity,
        privateRoomPrice = privateRoomPrice,
        returnDate = returnDate?.toFormattedString(),
        toddlerTourCost = toddlerTourCost,
        toddlerTourPrice = toddlerTourPrice,
        totalAdditionalCost = totalAdditionalCost,
        tour = tour.toTourDto(),
        tourOperator = tourOperator,
        tourUnitId = tourUnitId
    )
