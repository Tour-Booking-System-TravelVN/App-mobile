package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.TourProgramDto
import com.tanh.tourbooking.domain.model.TourProgram

fun TourProgram.toTourProgramDto(): TourProgramDto =
    TourProgramDto(
        day = day,
        description = description,
        locations = locations,
        mealDescription = mealDescription,
        id = id
    )

fun TourProgramDto.toTourProgram(): TourProgram =
    TourProgram(
        day = day,
        description = description,
        locations = locations,
        mealDescription = mealDescription,
        id = id
    )