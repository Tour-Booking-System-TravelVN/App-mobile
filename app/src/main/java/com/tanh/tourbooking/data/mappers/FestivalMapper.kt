package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.FestivalDto
import com.tanh.tourbooking.domain.model.Festival

fun Festival.toFestivalDto(): FestivalDto =
    FestivalDto(
        description = description,
        displayStatus = displayStatus,
        festivalName = festivalName,
        id = id
    )

fun FestivalDto.toFestival(): Festival =
    Festival(
        description = description,
        displayStatus = displayStatus,
        festivalName = festivalName,
        id = id
    )