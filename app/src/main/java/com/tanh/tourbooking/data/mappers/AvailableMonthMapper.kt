package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.AvailableMonthDto
import com.tanh.tourbooking.domain.model.AvailableMonth

fun AvailableMonth.toAvailableMonthDto(): AvailableMonthDto =
    AvailableMonthDto(
        month = month,
        year = year
    )

fun AvailableMonthDto.toAvailableMonth(): AvailableMonth =
    AvailableMonth(
        month = month,
        year = year
    )