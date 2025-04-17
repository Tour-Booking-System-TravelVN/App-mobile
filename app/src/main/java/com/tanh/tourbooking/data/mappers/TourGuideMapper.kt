package com.tanh.tourbooking.data.mappers

import com.tanh.tourbooking.data.model.dto.tour.TourGuideDto
import com.tanh.tourbooking.domain.model.Information

fun Information.TourGuide.toTourGuideDto(): TourGuideDto =
    TourGuideDto(
        address = address,
        citizenId = citizenId,
        dateOfBirth = dateOfBirth,
        firstname = firstname,
        gender = gender,
        id = id,
        lastname = lastname,
        nationality = nationality,
        note = note,
        passport = passport,
        phoneNumber = phoneNumber
    )

fun TourGuideDto.toTourGuide(): Information.TourGuide =
    Information.TourGuide(
        address = address,
        citizenId = citizenId,
        dateOfBirth = dateOfBirth,
        firstname = firstname,
        gender = gender,
        id = id,
        lastname = lastname,
        nationality = nationality,
        note = note,
        passport = passport,
        phoneNumber = phoneNumber
    )
