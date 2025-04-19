package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.TourUnitCalendarDto
import com.tanh.tourbooking.domain.model.TourUnitCalendar

fun TourUnitCalendar.toTourUnitCalendarDto(): TourUnitCalendarDto =
    TourUnitCalendarDto(
        adultTourPrice = adultTourPrice,
        availableCapacity = availableCapacity,
        babyTourPrice = babyTourPrice,
        childTourPrice = childTourPrice,
        toddlerTourPrice = toddlerTourPrice,
        departureDate = departureDate.toFormattedString(),
        discount = discount.toDiscountDto(),
        festival = festival.toFestivalDto(),
        maximumCapacity = maximumCapacity,
        privateRoomPrice = privateRoomPrice,
        returnDate = returnDate.toFormattedString(),
        tourUnitId = tourUnitId
    )

fun TourUnitCalendarDto.toTourUnitCalendar(): TourUnitCalendar =
    TourUnitCalendar(
        adultTourPrice = adultTourPrice,
        availableCapacity = availableCapacity,
        babyTourPrice = babyTourPrice,
        childTourPrice = childTourPrice,
        toddlerTourPrice = toddlerTourPrice,
        departureDate = departureDate.toLocalDate(),
        discount = discount.toDiscount(),
        festival = festival.toFestival(),
        maximumCapacity = maximumCapacity,
        privateRoomPrice = privateRoomPrice,
        returnDate = returnDate.toLocalDate(),
        tourUnitId = tourUnitId
    )